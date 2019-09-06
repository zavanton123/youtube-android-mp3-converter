package com.zavanton.yoump3.ui.main.activity.di

import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.di.AppComponentManager

object MainActivityComponentManager {

    private var mainActivityComponent: MainActivityComponent? = null

    fun getMainActivityComponent(): MainActivityComponent {
        return mainActivityComponent ?: DaggerMainActivityComponent
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
                mainActivityComponent = it
            }
    }

    fun clear() {
        Log.d()
        mainActivityComponent = null
    }
}