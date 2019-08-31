package com.zavanton.yoump3.ui.splash.di

import com.zavanton.yoump3.di.ActivityScope
import com.zavanton.yoump3.ui.splash.view.ISplashActivityViewModel
import com.zavanton.yoump3.ui.splash.view.SplashActivityViewModel
import com.zavanton.yoump3.ui.splash.view.SplashActivityViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        SplashActivityBindModule::class
    ]
)
interface SplashActivityComponent {

    fun inject(splashActivityViewModel: SplashActivityViewModelFactory)
}

@Module
interface SplashActivityBindModule {

    @Binds
    fun provideViewModel(impl: SplashActivityViewModel): ISplashActivityViewModel
}