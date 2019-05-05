package com.zavanton.yoump3.ui.download.di.module

import com.zavanton.yoump3.domain.interactor.convert.ConvertInteractor
import com.zavanton.yoump3.domain.interactor.download.DownloadInteractor
import com.zavanton.yoump3.domain.interactor.convert.IConvertInteractor
import com.zavanton.yoump3.domain.interactor.download.IDownloadInteractor
import com.zavanton.yoump3.ui.download.presenter.DownloadContract
import com.zavanton.yoump3.ui.download.presenter.DownloadPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class DownloadServiceBindModule {

    @Binds
    abstract fun bindPresenter(presenter: DownloadPresenter): DownloadContract.MvpPresenter

    @Binds
    abstract fun bindDownloadInteractor(interactor: DownloadInteractor): IDownloadInteractor

    @Binds
    abstract fun bindConvertInteractor(interactor: ConvertInteractor): IConvertInteractor
}