package com.zavanton.yoump3.ui.main.fragment.di

import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.ui.main.fragment.presenter.IMainFragmentPresenter
import com.zavanton.yoump3.ui.main.fragment.presenter.MainFragmentPresenter
import dagger.Module
import dagger.Provides

@Module
class MainFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(presenter: MainFragmentPresenter): IMainFragmentPresenter = presenter
}
