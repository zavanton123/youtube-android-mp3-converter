package com.zavanton.yoump3.ui.main.activity.di.module

import android.app.Activity
import android.content.Context
import com.zavanton.yoump3.di.qualifier.ActivityContext
import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.ui.main.activity.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityProvideModule(private val activity: MainActivity) {

    @ActivityScope
    @Provides
    @ActivityContext
    fun provideActivityContext(): Context = activity

    @ActivityScope
    @Provides
    fun provideActivity(): Activity = activity

    @ActivityScope
    @Provides
    fun provideMainActivity(): MainActivity = activity
}