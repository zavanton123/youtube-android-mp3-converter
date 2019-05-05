package com.zavanton.yoump3.ui.main.fragment.di.module

import com.zavanton.yoump3.ui.main.fragment.presenter.MainFragmentContract
import com.zavanton.yoump3.ui.main.fragment.presenter.MainFragmentPresenter
import dagger.Binds
import dagger.Module

@Module
interface MainFragmentBindModule {

    @Binds
    fun bindPresenter(presenter: MainFragmentPresenter): MainFragmentContract.MvpPresenter
}