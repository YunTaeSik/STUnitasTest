package com.example.stunitastest.presentation.di

import android.os.Build
import com.example.stunitastest.data.repository.SearchRepositoryImp
import com.example.stunitastest.domain.repository.SearchRepository
import com.example.stunitastest.domain.usecase.search.SearchUseCase
import com.example.stunitastest.domain.usecase.search.SearchUseCaseImp
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {

    @Provides
    @Model
    fun provideSearchRepository(): SearchRepository {
        return SearchRepositoryImp
    }

    @Provides
    @Model
    fun provideSearchUseCase(): SearchUseCase {
        return SearchUseCaseImp(provideSearchRepository())
    }

    @Provides
    @Model
    fun provideModel(): String {
        return "model"
    }

}