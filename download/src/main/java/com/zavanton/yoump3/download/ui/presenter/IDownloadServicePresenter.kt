package com.zavanton.yoump3.download.ui.presenter

import com.zavanton.yoump3.download.ui.view.IDownloadService

interface IDownloadServicePresenter {

    fun bind(downloadService: IDownloadService)
    fun unbind(downloadService: IDownloadService)

    fun onStartCommand()
}