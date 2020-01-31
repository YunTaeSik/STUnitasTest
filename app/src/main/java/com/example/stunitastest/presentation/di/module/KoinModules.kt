package com.example.stunitastest.presentation.di.module

import com.example.stunitastest.data.repository.SearchRepositoryImp
import com.example.stunitastest.domain.repository.SearchRepository
import com.example.stunitastest.domain.usecase.search.SearchUseCase
import com.example.stunitastest.domain.usecase.search.SearchUseCaseImp
import com.example.stunitastest.presentation.ui.intro.IntroViewModel
import com.example.stunitastest.presentation.ui.search.SearchAdapter
import com.example.stunitastest.presentation.ui.search.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImp }
    single<SearchUseCase> { SearchUseCaseImp(get()) }

}

var adapterModule = module {
    single<SearchAdapter> { SearchAdapter() }
}


var viewModelModule = module {
    viewModel {
        IntroViewModel()
    }

    viewModel {
        SearchViewModel(get())
    }
}

var moduleList = listOf(
    repositoryModule, adapterModule, viewModelModule
)