package com.example.stunitastest.presentation.di.module

import com.example.stunitastest.presentation.ui.search.SearchAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AdapterModule {
    @Provides
    @Singleton
    fun provideSearchAdapter(): SearchAdapter {
        return SearchAdapter()
    }
}