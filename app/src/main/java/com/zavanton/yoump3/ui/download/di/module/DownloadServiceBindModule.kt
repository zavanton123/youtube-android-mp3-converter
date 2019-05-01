package com.zavanton.yoump3.ui.download.di.module

import com.zavanton.yoump3.di.scope.ServiceScope
import com.zavanton.yoump3.ui.download.presenter.DownloadPresenter
import com.zavanton.yoump3.ui.download.presenter.IDownloadPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class DownloadServiceBindModule {

    @ServiceScope
    @Binds
    abstract fun bindDownloadPresenter(downloadPresenter: DownloadPresenter): IDownloadPresenter
}