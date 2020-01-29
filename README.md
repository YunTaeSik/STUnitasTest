## STUnitasTest 
[ST Unitas] 모바일 직군 사전 과제

## 과제 목표   
 - 필수 구현 목표와 제한 사항은 반드시 지켜야 하며, 그 외는 자유롭게 구현이 가능합니다.
 - 사용자가 검색어를 입력하고 이미지 검색 결과를 화면에 표시하는 어플리케이션을 만들어야 합니다.
 - 다음 이미지 검색 API 를 사용하여 개발해야 합니다. (API 키는 본인이 직접 발급 받으셔야 합니다.
 - https://developers.kakao.com/docs/restapi/search#%EC%9D%B4%EB%AF%B8%EC%A7%80-%EA%B2%80%EC%83%89
 - 검색 결과는 JSON 형식으로 사용해야 합니다.
 - 검색어 필드는 상단에 위치해야 합니다.
 - 검색 버튼은 없어야 하며, 1초 이상 검색어 입력이 없을 경우 검색 작업을 수행합니다.
 - 검색 결과가 없거나 오류가 발생했을 경우 사용자에게 알려야 합니다.
 - 검색 결과 이미지는 원본의 가로 세로 비율을 유지하여야 하며 이미지의 가로 사이즈는 화면 폭과 동일해야 합니다.
 - 페이징을 구현해야 합니다.
 - Loading 중일 때 Loading 을 나타낼 수 있는 Indicator (Progress) 를 노출해야합니다.

 **제한 사항**
  - 사용 언어 : Java
  - 사용 필수 라이브러리 : OkHttp3 / Retrofit2 / Jackson 혹은 Gson / Glide4
  - MVP 혹은 MVVM 사용  
  
**추가 구현 목표**
 - Kotlin 사용
 - Clean Architecture 사용
 - Android Architecture 및 Android Data Binding 사용
 - Rx2 사용
 - Dagger2 사용
 
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
 - [di (Dagger2.0)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/presentation/di)
 - [ui (View, ViewModel)](https://github.com/YunTaeSik/STUnitasTest/tree/master/app/src/main/java/com/example/stunitastest/presentation/i)
   
   
 **Domain Layer**  
   
  Domain Layer는 순수한 Java나 Kotlin 모듈입니다. 그 이유는 도메인 레이어에서 일어나는 이유는 실제로 사용자가 하는 일련의 행동들을 적용하는 것인데 이 역시 안드로이드에 의존할 필요가 없기 때문입니다. 
저는 Entity, Response, Repository를 담았습니다.
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

## App 구현 영상  

[![IMAGE ALT TEXT HERE](https://i9.ytimg.com/vi/P1HxcZGQ6Gs/mq2.jpg?sqp=CLufw_EF&rs=AOn4CLCU_7HCfnulVZBQxqQQxwi2uXKsoQ)](https://youtu.be/P1HxcZGQ6Gs)

## 에러 이미지 ( 네트워크 통신 안될때 / 검색결과 없을시 )

 <div>
  <img src="https://user-images.githubusercontent.com/23161645/73249346-0cc2a880-41f8-11ea-87d2-4835f0207adb.jpg" width = 250>
  <img src="https://user-images.githubusercontent.com/23161645/73249348-0d5b3f00-41f8-11ea-903f-6d781caab0a9.jpg" hspace=8 width = 250>
 </div>
