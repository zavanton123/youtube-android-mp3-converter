package com.zavanton.yoump3.ui.main.activity.di.module

import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.ui.main.activity.presenter.IMainActivityPresenter
import com.zavanton.yoump3.ui.main.activity.presenter.MainActivityPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityBindModule {

    @ActivityScope
    @Binds
    abstract fun providePresenter(presenter: MainActivityPresenter): IMainActivityPresenter
}