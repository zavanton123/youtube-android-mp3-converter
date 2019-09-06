package com.zavanton.yoump3.ui.main.activity.di

import com.zavanton.yoump3.core.di.ActivityScope
import com.zavanton.yoump3.ui.main.activity.view.MainActivityViewModel
import com.zavanton.yoump3.ui.main.fragment.di.component.MainFragmentComponent
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentProvideModule
import dagger.Module
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        MainActivityProvideModule::class,
        MainActivityBindModule::class
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