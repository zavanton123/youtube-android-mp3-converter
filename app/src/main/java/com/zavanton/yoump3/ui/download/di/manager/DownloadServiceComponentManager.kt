package com.zavanton.yoump3.ui.download.di.manager

import com.zavanton.yoump3.di.manager.ApplicationComponentManager
import com.zavanton.yoump3.ui.download.di.component.DownloadServiceComponent
import com.zavanton.yoump3.ui.download.di.module.DownloadServiceProvideModule
import com.zavanton.yoump3.ui.download.view.DownloadService
import com.zavanton.yoump3.utils.Logger

object DownloadServiceComponentManager {

    private var downloadServiceComponent: DownloadServiceComponent? = null

    fun inject(downloadService: DownloadService) {
        Logger.d("DownloadServiceComponentManager - inject")

        if (downloadServiceComponent == null) {
            downloadServiceComponent = ApplicationComponentManager.appComponent
                .plusDownloadServiceComponent(DownloadServiceProvideModule())
        }

        downloadServiceComponent?.inject(downloadService)
    }

    fun clear() {
        Logger.d("DownloadServiceComponentManager - clear")
        downloadServiceComponent = null
    }
}