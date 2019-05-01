package com.zavanton.yoump3.ui.main.fragment.di.module

import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.ui.main.fragment.presenter.IMainFragmentPresenter
import com.zavanton.yoump3.ui.main.fragment.presenter.MainFragmentPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class MainFragmentBindModule {

    @FragmentScope
    @Binds
    abstract fun providePresenter(presenter: MainFragmentPresenter): IMainFragmentPresenter
}
