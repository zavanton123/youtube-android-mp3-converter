package com.zavanton.yoump3.ui.download.di

import com.zavanton.yoump3.di.AppComponentManager
import com.zavanton.yoump3.ui.download.view.DownloadService
import com.zavanton.yoump3.core.utils.Log

object DownloadServiceComponentManager {

    private var downloadServiceComponent: DownloadServiceComponent? = null

    fun inject(downloadService: DownloadService) {
        Log.d()

        if (downloadServiceComponent == null) {
            downloadServiceComponent = AppComponentManager.appComponent
                .plusDownloadServiceComponent(DownloadServiceProvideModule())
        }

        downloadServiceComponent?.inject(downloadService)
    }

    fun clear() {
        Log.d()
        downloadServiceComponent = null
    }
}