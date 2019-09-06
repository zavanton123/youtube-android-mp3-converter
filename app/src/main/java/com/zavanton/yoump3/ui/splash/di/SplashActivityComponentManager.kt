package com.zavanton.yoump3.ui.splash.di

import androidx.fragment.app.FragmentActivity
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.di.AppComponentManager

object SplashActivityComponentManager {

    private var splashActivityComponent: SplashActivityComponent? = null

    fun get(activity: FragmentActivity): SplashActivityComponent? {
        Log.d()

        return splashActivityComponent ?: DaggerSplashActivityComponent
            .builder()
            .splashActivityProvideModule(SplashActivityProvideModule(activity))
            .appApi(AppComponentManager.getAppComponent())
            .schedulerApi(AppComponentManager.getAppComponent())
            .clipboardApi(AppComponentManager.getAppComponent())
            .networkApi(AppComponentManager.getAppComponent())
            .eventBusApi(AppComponentManager.getAppComponent())
            .notificationApi(AppComponentManager.getAppComponent())
            .conversionApi(AppComponentManager.getAppComponent())
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