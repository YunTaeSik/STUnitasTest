package com.example.stunitastest.presentation.di

import com.example.stunitastest.data.repository.SearchRepositoryImp
import com.example.stunitastest.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {

    @Provides
    fun provideSearchRepository(): SearchRepository {
        return SearchRepositoryImp
    }

}