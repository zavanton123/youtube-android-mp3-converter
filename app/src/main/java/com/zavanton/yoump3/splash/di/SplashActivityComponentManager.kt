package com.zavanton.yoump3.splash.di

import androidx.fragment.app.FragmentActivity
import com.zavanton.yoump3.core.di.CoreComponentManager
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.download.di.DownloadServiceComponentManager

object SplashActivityComponentManager {

    private var splashActivityComponent: SplashActivityComponent? = null

    fun getSplashActivityComponent(activity: FragmentActivity): SplashActivityComponent? =
        splashActivityComponent
            ?: DaggerSplashActivityComponent.builder()
                .splashActivityProvideModule(SplashActivityProvideModule(activity))
                .appApi(CoreComponentManager.getCoreComponent())
                .schedulerApi(CoreComponentManager.getCoreComponent())
                .clipboardApi(CoreComponentManager.getCoreComponent())
                .networkApi(CoreComponentManager.getCoreComponent())
                .eventBusApi(DownloadServiceComponentManager.getDownloadServiceComponent())
                .notificationApi(CoreComponentManager.getCoreComponent())
                .build()
                .also {
                    splashActivityComponent = it
                }

    fun clearSplashActivityComponent() {
        Log.d()
        splashActivityComponent = null
    }
}