package com.zavanton.yoump3.ui.download.di

import com.zavanton.yoump3.core.di.CoreComponentManager
import com.zavanton.yoump3.core.utils.Log

object DownloadServiceComponentManager {

    private var downloadServiceComponent: DownloadServiceComponent? = null

    fun getDownloadServiceComponent(): DownloadServiceComponent {

        return downloadServiceComponent ?: DaggerDownloadServiceComponent
            .builder()
            .appApi(CoreComponentManager.getCoreComponent())
            .schedulerApi(CoreComponentManager.getCoreComponent())
            .clipboardApi(CoreComponentManager.getCoreComponent())
            .networkApi(CoreComponentManager.getCoreComponent())
            .eventBusApi(CoreComponentManager.getCoreComponent())
            .notificationApi(CoreComponentManager.getCoreComponent())
            .conversionApi(CoreComponentManager.getCoreComponent())
            .build()
            .also {
                downloadServiceComponent = it
            }
    }

    fun clear() {
        Log.d()
        downloadServiceComponent = null
    }
}