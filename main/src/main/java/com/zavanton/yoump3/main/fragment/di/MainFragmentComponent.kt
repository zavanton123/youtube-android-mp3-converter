package com.zavanton.yoump3.main.fragment.di

import com.zavanton.yoump3.core.di.FragmentScope
import com.zavanton.yoump3.main.fragment.ui.presenter.IMainFragmentPresenter
import com.zavanton.yoump3.main.fragment.ui.presenter.MainFragmentPresenter
import com.zavanton.yoump3.main.fragment.ui.view.MainFragmentViewModel
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

    fun inject(mainFragment: MainFragmentViewModel)
}

@Module
interface MainFragmentBindModule {

    @Binds
    fun bindPresenter(presenter: MainFragmentPresenter): IMainFragmentPresenter
}

@Module
class MainFragmentProvideModule