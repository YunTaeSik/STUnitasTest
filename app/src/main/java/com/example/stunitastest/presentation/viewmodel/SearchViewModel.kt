package com.example.stunitastest.presentation.viewmodel

import android.app.Application
import com.example.stunitastest.R
import com.example.stunitastest.data.repository.SearchRepositoryImp
import com.example.stunitastest.domain.repository.SearchRepository
import com.example.stunitastest.presentation.viewmodel.base.BaseViewModel

class SearchViewModel(application: Application) : BaseViewModel(application) {
    private val searchRepository: SearchRepository by lazy {
        SearchRepositoryImp
    }

    fun getImages(query: String, page: Int, size: Int){
        addDisposable(searchRepository.getImages(query,"accuracy ",page,size).subscribe({
            //it.meta.

           _isLoading.value = false
        }, {
            _toastMessage.value = context.getString(R.string.error_message)
            _isLoading.value = false
        }))
    }

}