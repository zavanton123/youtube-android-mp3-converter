package com.zavanton.yoump3.ui.download.di.module

import com.zavanton.yoump3.domain.interactor.ConvertInteractor
import com.zavanton.yoump3.domain.interactor.DownloadInteractor
import com.zavanton.yoump3.domain.interactor.IConvertInteractor
import com.zavanton.yoump3.domain.interactor.IDownloadInteractor
import com.zavanton.yoump3.ui.download.presenter.DownloadPresenter
import com.zavanton.yoump3.ui.download.presenter.IDownloadPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class DownloadServiceBindModule {

    @Binds
    abstract fun bindPresenter(presenter: DownloadPresenter): IDownloadPresenter

    @Binds
    abstract fun bindDownloadInteractor(interactor: DownloadInteractor): IDownloadInteractor

    @Binds
    abstract fun bindConvertInteractor(interactor: ConvertInteractor): IConvertInteractor
}