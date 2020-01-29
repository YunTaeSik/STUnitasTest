package com.example.stunitastest.presentation.base

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stunitastest.BaseApplication
import com.example.stunitastest.extension.log
import com.example.stunitastest.presentation.di.component.DaggerAppComponent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected val context: Context = application.applicationContext
    private val compositeDisposable = CompositeDisposable()

    protected var _isLoading = MutableLiveData<Boolean>()
    protected var _toastMessage = MutableLiveData<String>()

    val isLoading: LiveData<Boolean> get() = _isLoading
    val toastMessage: LiveData<String> get() = _toastMessage

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clear() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clear()
        super.onCleared()
    }
}