package com.example.stunitastest.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.stunitastest.R
import com.example.stunitastest.data.repository.SearchRepositoryImp
import com.example.stunitastest.domain.repository.SearchRepository
import com.example.stunitastest.domain.response.SearchResponse
import com.example.stunitastest.entity.Document
import com.example.stunitastest.extension.addAll
import com.example.stunitastest.extension.clear
import com.example.stunitastest.extension.log
import com.example.stunitastest.presentation.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.math.log

class SearchViewModel(application: Application) : BaseViewModel(application) {
    private var searchDisposable: Disposable? = null

    private val searchRepository: SearchRepository by lazy {
        SearchRepositoryImp
    }

    private var _query = MutableLiveData<String>()
    private var _sort = MutableLiveData<String>()
    private var _size = MutableLiveData<Int>()
    private var _page = MutableLiveData<Int>()

    private var _listDocument = MutableLiveData<List<Document>>()

    val query: LiveData<String> get() = _query
    val sort: LiveData<String> get() = _sort
    val size: LiveData<Int> get() = _size
    val page: LiveData<Int> get() = _page

    val listDocument: LiveData<List<Document>> get() = _listDocument

    fun setQuery(query: String) {
        _query.value = query
    }

    fun setPage(page: Int) {
        _page.value = page
    }

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


    fun getImages() {
        if (query.value != null) {
            _isLoading.postValue(true)
            addDisposable(
                searchRepository.getImages(
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


}