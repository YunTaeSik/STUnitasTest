package com.example.stunitastest.extension

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.addAll(values: List<T>) {
    val value: ArrayList<T> = this.value as ArrayList<T>
    value.addAll(values)
    this.postValue(value)
}