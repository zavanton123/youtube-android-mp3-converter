package com.zavanton.yoump3.ui.splash.di

import com.zavanton.yoump3.di.ActivityScope
import com.zavanton.yoump3.ui.splash.presenter.ISplashActivityPresenter
import com.zavanton.yoump3.ui.splash.presenter.SplashActivityPresenter
import com.zavanton.yoump3.ui.splash.view.SplashActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        SplashActivityProvideModule::class,
        SplashActivityBindModule::class
    ]
)
interface SplashActivityComponent {

    fun inject(splashActivityViewModel: SplashActivityViewModel)
}

@Module
interface SplashActivityBindModule {

    @Binds
    fun bindPresenter(impl: SplashActivityPresenter): ISplashActivityPresenter
}

@Module
class SplashActivityProvideModule