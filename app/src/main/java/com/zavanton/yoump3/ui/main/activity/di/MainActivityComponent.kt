package com.zavanton.yoump3.ui.main.activity.di

import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.ui.main.fragment.di.MainFragmentComponent
import com.zavanton.yoump3.ui.main.fragment.di.MainFragmentModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        MainActivityModule::class
    ]
)
interface MainActivityComponent {

    fun plusMainFragmentComponent(mainFragmentModule: MainFragmentModule): MainFragmentComponent
}