package com.zavanton.yoump3.main.activity.di

import com.zavanton.yoump3.core.di.CoreComponentManager
import com.zavanton.yoump3.core.utils.Log

object MainActivityComponentManager {

    private var mainActivityComponent: MainActivityComponent? = null

    fun getMainActivityComponent(): MainActivityComponent {
        return mainActivityComponent
            ?: DaggerMainActivityComponent.builder()
            .appApi(CoreComponentManager.getCoreComponent())
            .schedulerApi(CoreComponentManager.getCoreComponent())
            .clipboardApi(CoreComponentManager.getCoreComponent())
            .networkApi(CoreComponentManager.getCoreComponent())
            .eventBusApi(CoreComponentManager.getCoreComponent())
            .notificationApi(CoreComponentManager.getCoreComponent())
            .build()
            .also {
                mainActivityComponent = it
            }
    }

    fun clear() {
        Log.d()
        mainActivityComponent = null
    }
}