package com.zavanton.yoump3.main.activity.di

import com.zavanton.yoump3.core.di.CoreComponentManager
import com.zavanton.yoump3.core.utils.Log

object MainActivityComponentManager {

    private var mainActivityComponent: MainActivityComponent? = null

    fun getMainActivityComponent(): MainActivityComponent =
        mainActivityComponent ?: DaggerMainActivityComponent.builder()
            .clipboardApi(CoreComponentManager.getCoreComponent())
            .eventBusApi(CoreComponentManager.getCoreComponent())
            .build()
            .also {
                mainActivityComponent = it
            }

    fun clearMainActivityComponent() {
        Log.d()
        mainActivityComponent = null
    }
}