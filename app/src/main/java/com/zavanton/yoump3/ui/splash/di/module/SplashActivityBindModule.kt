package com.zavanton.yoump3.ui.splash.di.module

import com.zavanton.yoump3.ui.splash.presenter.ISplashActivityPresenter
import com.zavanton.yoump3.ui.splash.presenter.SplashActivityPresenter
import dagger.Binds
import dagger.Module

@Module
interface SplashActivityBindModule {

    @Binds
    fun bindPresenter(impl: SplashActivityPresenter): ISplashActivityPresenter
}