
 ## 과제 구현
 
 ### 사용언어
 Kotlin
 
 ### Architecture
 Clean Architecture  
 
 <div>
  <img src="https://user-images.githubusercontent.com/23161645/73226529-c3e90080-41b3-11ea-9f21-992c4c60335f.png" width = 250>
  <img src="http://miro.medium.com/max/1258/1*a-AUcEVdyRJhIepo9JyJBw.png" hspace=8 width = 250>
 </div>
     
     
 **Presentation Layer**  
   
 Presentation 레이어는 의존성이 높은 UI 레벨의 레이어입니다.  
현재 저는 MVVM패턴을 사용하고있습니다.  
 - [base (각종 Base 클래스)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/presentation/base)
 - [bidingAdapter (Android BindingAdapter)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/presentation/bindingAdapter) 
 - [di (Koin)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/presentation/di)
 - [ui (View, ViewModel)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/presentation/ui)
   
   
 **Domain Layer**  
   
  Domain Layer는 순수한 Java나 Kotlin 모듈입니다. 그 이유는 도메인 레이어에서 일어나는 이유는 실제로 사용자가 하는 일련의 행동들을 적용하는 것인데 이 역시 안드로이드에 의존할 필요가 없기 때문입니다. 
저는 Entity, Response , Repository, UseCase를 담았습니다.
  - [entity (순수한 Entity값)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/domain/entity)
  - [response (API Response 객체)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/domain/response)
  - [repository (API Interface)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/domain/repository)
   - [usecase (UseCase Presentation과 연결부분)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/domain/usecase)
   
   
 **Data Layer**  
   
Data Layer 에서는 Domain Layer를 알고 있으므로  Domain Layer에 정의된 Repository를 실제로 구현을 하는 것입니다.   
여기에서는 Data Source에의 의존성이 생기므로 안드로이드 의존성이 생길 수 있습니다.
  - [repository (Domain repository를 실제로 구현하는부분)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/data/repository)
  - [source (Retrofit2.0 Service 구현)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/data/source/remote)

  
### Retrofit2 Example

[SearchService.kt](https://github.com/YunTaeSik/STUnitasTest/blob/master/app/src/main/java/com/example/stunitastest/data/source/remote/SearchService.kt)
```Kotlin
interface SearchService {
    @GET("/v2/search/image")
    fun getImages(
        @Header("Authorization") serverAuth: String,
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?
        ): Observable<SearchResponse>


    object Creator {
        private const val URL = "https://dapi.kakao.com"

        fun create(): SearchService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()
            return retrofit.create(SearchService::class.java)
        }
    }
}
```


### Android Data Binding Example, Glide 
[ImageBindingAdapter.kt](https://github.com/YunTaeSik/STUnitasTest/blob/master/app/src/main/res/layout/item_search.xml)
```Kotlin
    @JvmStatic
    @BindingAdapter("srcCompat")
    fun srcCompat(view: ImageView, url: String) {
        val circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.start()

        Glide.with(view.context).load(url).thumbnail(0.1f)
            .placeholder(circularProgressDrawable)
            .error(R.drawable.img_error)
            .into(view)
    }
```

[item_search.xml](https://github.com/YunTaeSik/STUnitasTest/blob/master/app/src/main/java/com/example/stunitastest/presentation/bindingAdapter/ImageBindingAdapter.kt)
```xml
<?xml version="1.0" encoding="utf-8"?>

<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data class="SearchItemBinding">

        <variable
            name="data"
            type="com.example.stunitastest.domain.entity.Document" />

    </data>

    <com.google.android.material.card.MaterialCardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        android:minHeight="100dp"
        android:orientation="horizontal"
        app:cardBackgroundColor="@color/colorPrimary"
        app:cardCornerRadius="4dp">

        <androidx.appcompat.widget.AppCompatImageView
            android:id="@+id/image"
            srcCompat="@{data.image_url}"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:adjustViewBounds="true"
            android:background="@color/cardBackground" />

    </com.google.android.material.card.MaterialCardView>
</layout>
```

### RxAndroid Example  
[SearchViewModel.kt](https://github.com/YunTaeSik/STUnitasTest/blob/master/app/src/main/java/com/example/stunitastest/presentation/ui/search/SearchViewModel.kt)
```Kotlin
    /**
     * 검색
     */
    fun search(query: String) {
        setQuery(query)
        searchDisposable?.dispose()
        if (query.isNotEmpty()) {
            searchDisposable =
                Observable.timer(1, TimeUnit.SECONDS)
                    .subscribe({
                        _listDocument.clear()
                        getImages()
                    }, {
                        it.printStackTrace()
                    })
        }
    }

    /**
     * 이미지목록 가져오기
     */
    fun getImages() {
        if (query.value != null) {
            _isLoading.postValue(true)
            addDisposable(
                searchUseCase.getImages(
                    query.value!!,
                    sort.value,
                    page.value,
                    size.value
                ).subscribe({

                    if (it.documents?.size == 0) {
                        _toastMessage.postValue(context.getString(R.string.error_query_size_null_message))
                    }
                    if (it.meta?.total_count != _listDocument.value?.size) {
                        _listDocument.addAll(it.documents!!)
                    } else {

                    }

                    _isLoading.postValue(false)
                }, {
                    it.printStackTrace()
                    _toastMessage.postValue(context.getString(R.string.error_message))
                    _isLoading.postValue(false)
                })
            )
        } else {
            _toastMessage.postValue(context.getString(R.string.error_query_text_null_message))
        }
    }
```
  
### Koin (Dagger2 대안) Example  
DI는 Dagger2.0대신 Koin으로 구현하였습니다.
  
**module**  
```Kotlin
val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImp }
    single<SearchUseCase> { SearchUseCaseImp(get()) }

}

var adapterModule = module {
    single<SearchAdapter> { SearchAdapter() }
}


var viewModelModule = module {
    viewModel {
        IntroViewModel(androidApplication())
    }

    viewModel {
        SearchViewModel(androidApplication(), get())
    }
}

var moduleList = listOf(
    repositoryModule, adapterModule, viewModelModule
)
```  
  
**start**
```Kotlin
class BaseApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(moduleList)
        }

    }
}
```  
  
**inject**  
```Kotlin
    private val searchAdapter: SearchAdapter by inject()
    private val model: SearchViewModel by viewModel()
```

## App 구현 영상  

[![IMAGE ALT TEXT HERE](https://user-images.githubusercontent.com/23161645/73340055-5f19cd00-42bd-11ea-9625-c64ee3ed4271.png)](https://youtu.be/P1HxcZGQ6Gs)

## 에러 이미지 ( 네트워크 통신 안될때 / 검색결과 없을시 )

 <div>
  <img src="https://user-images.githubusercontent.com/23161645/73249346-0cc2a880-41f8-11ea-87d2-4835f0207adb.jpg" width = 250>
  <img src="https://user-images.githubusercontent.com/23161645/73249348-0d5b3f00-41f8-11ea-903f-6d781caab0a9.jpg" hspace=8 width = 250>
 </div>
 
 ## 기타  
[YtsCleanArchitecture](https://github.com/YunTaeSik/YtsCleanArchitecture)  
현재의 Domain과 Data를 모듈화하여 새로 만든 프로젝트입니다.  
Domain과 Data를 모듈화 함으로써 Clean Architecture의 장점인 UI를 독립시키고 데이터베이스를 분리시키고,  
외부적인 설정에 독립적인 구조를 적용하여 프레임워크에 의존적이지않은 독립적인 코드를 짤 수 있습니다.
