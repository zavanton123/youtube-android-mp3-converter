package com.zavanton.yoump3.ui.main.activity.di.component

import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentProvideModule
import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.ui.main.activity.MainActivity
import com.zavanton.yoump3.ui.main.activity.di.module.MainActivityBindModule
import com.zavanton.yoump3.ui.main.activity.di.module.MainActivityProvideModule
import com.zavanton.yoump3.ui.main.fragment.di.component.MainFragmentComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        MainActivityProvideModule::class,
        MainActivityBindModule::class
    ]
)
interface MainActivityComponent {

    fun plusMainFragmentComponent(mainFragmentProvideModule: MainFragmentProvideModule): MainFragmentComponent

    fun inject(mainActivity: MainActivity)
}