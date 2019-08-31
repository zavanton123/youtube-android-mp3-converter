package com.zavanton.yoump3.ui.main.fragment.di.component

import com.zavanton.yoump3.di.FragmentScope
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentBindModule
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentProvideModule
import com.zavanton.yoump3.ui.main.fragment.view.MainFragmentViewModel
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