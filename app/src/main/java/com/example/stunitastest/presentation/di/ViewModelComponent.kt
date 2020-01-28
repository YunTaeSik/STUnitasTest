package com.example.stunitastest.presentation.di

import com.example.stunitastest.presentation.base.BaseViewModel
import dagger.Component
import dagger.android.AndroidInjectionModule

@Component(
    modules = [
        RepositoryModule::class
    ]
)
interface ViewModelComponent {

    fun inject(model: BaseViewModel)
}