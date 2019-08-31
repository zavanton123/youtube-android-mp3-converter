package com.zavanton.yoump3.ui.download.di.manager

import com.zavanton.yoump3.di.manager.ApplicationComponentManager
import com.zavanton.yoump3.ui.download.di.component.DownloadServiceComponent
import com.zavanton.yoump3.ui.download.di.module.DownloadServiceProvideModule
import com.zavanton.yoump3.ui.download.view.DownloadService
import com.zavanton.yoump3.utils.Log

object DownloadServiceComponentManager {

    private var downloadServiceComponent: DownloadServiceComponent? = null

    fun inject(downloadService: DownloadService) {
        Log.d()

        if (downloadServiceComponent == null) {
            downloadServiceComponent = ApplicationComponentManager.appComponent
                .plusDownloadServiceComponent(DownloadServiceProvideModule())
        }

        downloadServiceComponent?.inject(downloadService)
    }

    fun clear() {
        Log.d()
        downloadServiceComponent = null
    }
}