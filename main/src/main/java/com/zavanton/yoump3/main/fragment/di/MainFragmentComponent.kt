package com.zavanton.yoump3.main.fragment.di

import com.zavanton.yoump3.core.di.FragmentScope
import com.zavanton.yoump3.main.fragment.ui.viewModel.IMainFragmentViewModel
import com.zavanton.yoump3.main.fragment.ui.viewModel.MainFragmentViewModel
import com.zavanton.yoump3.main.fragment.ui.viewModel.MainFragmentViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        MainFragmentProvideModule::class,
        MainFragmentBindModule::class
    ]
)
interface MainFragmentComponent {

    fun inject(factory: MainFragmentViewModelFactory)
}

@Module
interface MainFragmentBindModule {

    @Binds
    fun provideViewModel(impl: MainFragmentViewModel): IMainFragmentViewModel
}

@Module
class MainFragmentProvideModule