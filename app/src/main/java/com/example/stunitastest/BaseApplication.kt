package com.example.stunitastest

import android.content.Context
import androidx.multidex.MultiDex
import com.example.stunitastest.domain.repository.SearchRepository
import com.example.stunitastest.extension.log
import com.example.stunitastest.presentation.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class BaseApplication : DaggerApplication() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }


}