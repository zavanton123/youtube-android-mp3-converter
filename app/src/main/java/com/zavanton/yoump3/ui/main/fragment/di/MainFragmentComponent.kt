package com.zavanton.yoump3.ui.main.fragment.di

import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.ui.main.fragment.MainFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        MainFragmentModule::class
    ]
)
interface MainFragmentComponent {

    fun inject(mainFragment: MainFragment)
}