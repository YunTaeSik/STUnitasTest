package com.example.stunitastest.presentation.di

import com.example.stunitastest.presentation.base.BaseViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class
    ]
)
interface ViewModelComponent {

    fun inject(model: BaseViewModel)

    @Component.Builder
    interface Builder {
        fun build() : ViewModelComponent
        fun repositoryModule(module : RepositoryModule) : Builder
    }
}