package com.example.stunitastest.presentation.di

import com.example.stunitastest.BaseApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<BaseApplication>

}