package com.example.stunitastest.presentation.di.module

import com.example.stunitastest.presentation.ui.intro.IntroActivity
import com.example.stunitastest.presentation.ui.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {
    @ContributesAndroidInjector
    fun introActivityInjector(): IntroActivity

    @ContributesAndroidInjector
    fun searchActivityInjector(): SearchActivity

}