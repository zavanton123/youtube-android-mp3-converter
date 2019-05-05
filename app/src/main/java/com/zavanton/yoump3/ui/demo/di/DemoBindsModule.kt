package com.zavanton.yoump3.ui.demo.di

import com.zavanton.yoump3.ui.demo.Demo
import com.zavanton.yoump3.ui.demo.DemoPresenter
import dagger.Binds
import dagger.Module

@Module
interface DemoBindsModule {

    @Binds
    fun bindPresenter(presenter: DemoPresenter): Demo.MvpPresenter
}