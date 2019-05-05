package com.zavanton.yoump3.ui.main.fragment.di.component

import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.ui.main.fragment.MainFragment
import com.zavanton.yoump3.ui.main.fragment.MainFragmentViewModel
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentBindModule
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentProvideModule
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        MainFragmentProvideModule::class,
        MainFragmentBindModule::class
    ]
)
interface MainFragmentComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(mainFragment: MainFragmentViewModel)
}