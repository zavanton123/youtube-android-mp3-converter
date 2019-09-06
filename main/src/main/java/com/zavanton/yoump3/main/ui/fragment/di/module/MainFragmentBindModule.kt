package com.zavanton.yoump3.main.ui.fragment.di.module

import com.zavanton.yoump3.main.ui.fragment.presenter.IMainFragmentPresenter
import com.zavanton.yoump3.main.ui.fragment.presenter.MainFragmentPresenter
import dagger.Binds
import dagger.Module

@Module
interface MainFragmentBindModule {

    @Binds
    fun bindPresenter(presenter: MainFragmentPresenter): IMainFragmentPresenter
}