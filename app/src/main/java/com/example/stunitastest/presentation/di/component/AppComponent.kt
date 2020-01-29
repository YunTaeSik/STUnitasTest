package com.example.stunitastest.presentation.di.component

import com.example.stunitastest.BaseApplication
import com.example.stunitastest.presentation.di.module.ActivityModule
import com.example.stunitastest.presentation.di.module.AdapterModule
import com.example.stunitastest.presentation.di.module.RepositoryModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        AdapterModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<BaseApplication>

}

