package com.zavanton.yoump3.ui.demo.di

import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.ui.demo.Demo
import com.zavanton.yoump3.ui.demo.DemoPresenter
import dagger.Module
import dagger.Provides

@Module
class DemoModule {

    @ActivityScope
    @Provides
    fun providePresenter(presenter: DemoPresenter): Demo.MvpPresenter {
        return presenter
    }
}
