package com.zavanton.yoump3.main.activity.di

import com.zavanton.yoump3.core.di.CoreComponentManager
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.download.di.DownloadServiceComponentManager

object MainActivityComponentManager {

    private var mainActivityComponent: MainActivityComponent? = null

    fun getMainActivityComponent(): MainActivityComponent =
        mainActivityComponent ?: DaggerMainActivityComponent.builder()
            .clipboardApi(CoreComponentManager.getCoreComponent())
            .eventBusApi(DownloadServiceComponentManager.getDownloadServiceComponent())
            .build()
            .also {
                mainActivityComponent = it
            }

    fun clearMainActivityComponent() {
        Log.d()
        mainActivityComponent = null
    }
}