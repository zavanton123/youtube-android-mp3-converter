package com.zavanton.yoump3.di

import androidx.fragment.app.FragmentActivity
import com.zavanton.yoump3.core.di.CoreComponentManager
import com.zavanton.yoump3.core.utils.Log

object SplashActivityComponentManager {

    private var splashActivityComponent: SplashActivityComponent? = null

    fun get(activity: FragmentActivity): SplashActivityComponent? {
        Log.d()

        return splashActivityComponent ?: DaggerSplashActivityComponent
            .builder()
            .splashActivityProvideModule(SplashActivityProvideModule(activity))
            .appApi(CoreComponentManager.getCoreComponent())
            .schedulerApi(CoreComponentManager.getCoreComponent())
            .clipboardApi(CoreComponentManager.getCoreComponent())
            .networkApi(CoreComponentManager.getCoreComponent())
            .eventBusApi(CoreComponentManager.getCoreComponent())
            .notificationApi(CoreComponentManager.getCoreComponent())
            .build()
            .also {
                splashActivityComponent = it
            }
    }

    fun clear() {
        Log.d()
        splashActivityComponent = null
    }
}