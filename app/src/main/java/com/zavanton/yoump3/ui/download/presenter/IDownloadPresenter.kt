package com.zavanton.yoump3.ui.download.presenter

import com.zavanton.yoump3.ui.download.service.IDownloadService

interface IDownloadPresenter {

    fun bind(downloadService: IDownloadService)
    fun unbind(downloadService: IDownloadService)

    fun onStartCommand()
}