package com.zavanton.yoump3.download.di

import com.zavanton.yoump3.core.di.CoreComponentManager
import com.zavanton.yoump3.core.utils.Log

object DownloadServiceComponentManager {

    private var downloadServiceComponent: DownloadServiceComponent? = null

    fun getDownloadServiceComponent(): DownloadServiceComponent =
        downloadServiceComponent ?: DaggerDownloadServiceComponent
            .builder()
            .appApi(CoreComponentManager.getCoreComponent())
            .schedulerApi(CoreComponentManager.getCoreComponent())
            .networkApi(CoreComponentManager.getCoreComponent())
            .notificationApi(CoreComponentManager.getCoreComponent())
            .build()
            .also {
                downloadServiceComponent = it
            }

    fun clear() {
        Log.d()
        downloadServiceComponent = null
    }
}