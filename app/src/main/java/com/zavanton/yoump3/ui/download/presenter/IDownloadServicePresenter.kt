package com.zavanton.yoump3.ui.download.presenter

import com.zavanton.yoump3.ui.download.view.IDownloadService

interface IDownloadServicePresenter {

    fun bind(downloadService: IDownloadService)
    fun unbind(downloadService: IDownloadService)

    fun onStartCommand()
}