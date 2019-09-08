package com.zavanton.yoump3.main.ui.fragment.di

import com.zavanton.yoump3.core.di.FragmentScope
import com.zavanton.yoump3.main.ui.fragment.presenter.IMainFragmentPresenter
import com.zavanton.yoump3.main.ui.fragment.presenter.MainFragmentPresenter
import com.zavanton.yoump3.main.ui.fragment.view.MainFragmentViewModel
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