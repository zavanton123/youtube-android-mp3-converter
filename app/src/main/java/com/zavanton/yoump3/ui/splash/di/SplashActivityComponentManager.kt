package com.zavanton.yoump3.ui.splash.di

import com.zavanton.yoump3.di.AppComponentManager
import com.zavanton.yoump3.utils.Log

object SplashActivityComponentManager {

    private var splashActivityComponent: SplashActivityComponent? = null

    fun get(): SplashActivityComponent? {
        Log.d()
        if (splashActivityComponent == null) {
            splashActivityComponent = AppComponentManager.appComponent
                .plusSplashActivityComponent()
        }

        return splashActivityComponent
    }

    fun clear() {
        Log.d()
        splashActivityComponent = null
    }
}