package com.zavanton.yoump3.ui.main.fragment.di

import com.zavanton.yoump3.di.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        MainFragmentModule::class
    ]
)
interface MainFragmentComponent