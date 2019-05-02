package com.zavanton.yoump3.ui.main.fragment.di.component

import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.ui.main.fragment.view.MainFragment
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
}