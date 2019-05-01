package com.zavanton.yoump3.ui.main.activity.di

import android.app.Activity
import android.content.Context
import com.zavanton.yoump3.di.qualifier.ActivityContext
import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.ui.main.activity.presenter.IMainActivityPresenter
import com.zavanton.yoump3.ui.main.activity.presenter.MainActivityPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val activity: Activity) {

    @ActivityScope
    @Provides
    @ActivityContext
    fun provideActivityContext(): Context = activity

    @ActivityScope
    @Provides
    fun provideActivity(): Activity = activity

    @ActivityScope
    @Provides
    fun providePresenter(presenter: MainActivityPresenter): IMainActivityPresenter = presenter

}