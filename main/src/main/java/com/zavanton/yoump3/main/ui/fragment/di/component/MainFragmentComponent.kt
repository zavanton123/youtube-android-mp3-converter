package com.zavanton.yoump3.main.ui.fragment.di.component

import com.zavanton.yoump3.core.di.FragmentScope
import com.zavanton.yoump3.main.ui.fragment.di.module.MainFragmentBindModule
import com.zavanton.yoump3.main.ui.fragment.di.module.MainFragmentProvideModule
import com.zavanton.yoump3.main.ui.fragment.view.MainFragmentViewModel
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        MainFragmentProvideModule::class,
        MainFragmentBindModule::class
    ]
)
interface MainFragmentComponent {

    fun inject(mainFragment: MainFragmentViewModel)
}