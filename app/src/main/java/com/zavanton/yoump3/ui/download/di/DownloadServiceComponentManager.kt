package com.zavanton.yoump3.ui.download.di

import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.di.AppComponentManager

object DownloadServiceComponentManager {

    private var downloadServiceComponent: DownloadServiceComponent? = null

    fun getDownloadServiceComponent(): DownloadServiceComponent {

        return downloadServiceComponent ?: DaggerDownloadServiceComponent
            .builder()
            .appApi(AppComponentManager.getAppComponent())
            .schedulerApi(AppComponentManager.getAppComponent())
            .clipboardApi(AppComponentManager.getAppComponent())
            .networkApi(AppComponentManager.getAppComponent())
            .eventBusApi(AppComponentManager.getAppComponent())
            .notificationApi(AppComponentManager.getAppComponent())
            .conversionApi(AppComponentManager.getAppComponent())
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