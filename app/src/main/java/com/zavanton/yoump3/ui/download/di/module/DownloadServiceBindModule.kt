package com.zavanton.yoump3.ui.download.di.module

import com.zavanton.yoump3.ui.download.presenter.DownloadPresenter
import com.zavanton.yoump3.ui.download.presenter.IDownloadPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class DownloadServiceBindModule {

    @Binds
    abstract fun bindPresenter(presenter: DownloadPresenter): IDownloadPresenter
}