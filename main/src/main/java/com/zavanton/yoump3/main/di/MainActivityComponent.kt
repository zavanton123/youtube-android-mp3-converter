package com.zavanton.yoump3.main.di

import com.zavanton.yoump3.core.di.*
import com.zavanton.yoump3.main.ui.activity.view.MainActivityViewModel
import com.zavanton.yoump3.main.ui.fragment.di.MainFragmentComponent
import com.zavanton.yoump3.main.ui.fragment.di.MainFragmentProvideModule
import dagger.Component
import dagger.Module

@ActivityScope
@Component(
    modules = [
        MainActivityProvideModule::class,
        MainActivityBindModule::class
    ],
    dependencies = [
        AppApi::class,
        SchedulerApi::class,
        ClipboardApi::class,
        NetworkApi::class,
        EventBusApi::class,
        NotificationApi::class
    ]
)
interface MainActivityComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)

    fun plusMainFragmentComponent(module: MainFragmentProvideModule): MainFragmentComponent
}

@Module
class MainActivityProvideModule

@Module
abstract class MainActivityBindModule