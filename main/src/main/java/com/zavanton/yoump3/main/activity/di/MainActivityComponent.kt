package com.zavanton.yoump3.main.activity.di

import com.zavanton.yoump3.core.di.ActivityScope
import com.zavanton.yoump3.core.di.ClipboardApi
import com.zavanton.yoump3.core.di.EventBusApi
import com.zavanton.yoump3.main.activity.ui.MainActivityViewModel
import com.zavanton.yoump3.main.fragment.di.MainFragmentComponent
import com.zavanton.yoump3.main.fragment.di.MainFragmentProvideModule
import dagger.Component
import dagger.Module

@ActivityScope
@Component(
    dependencies = [
        ClipboardApi::class,
        EventBusApi::class
    ]
)
interface MainActivityComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)

    fun plusMainFragmentComponent(module: MainFragmentProvideModule): MainFragmentComponent
}