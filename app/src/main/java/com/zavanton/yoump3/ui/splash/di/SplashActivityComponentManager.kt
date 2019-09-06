package com.zavanton.yoump3.ui.splash.di

import androidx.fragment.app.FragmentActivity
import com.zavanton.yoump3.di.AppComponentManager
import com.zavanton.yoump3.core.utils.Log

object SplashActivityComponentManager {

    private var splashActivityComponent: SplashActivityComponent? = null

    fun get(activity: FragmentActivity): SplashActivityComponent? {
        Log.d()
        if (splashActivityComponent == null) {
            splashActivityComponent = AppComponentManager.appComponent
                .plusSplashActivityComponent(SplashActivityProvideModule(activity))
        }

        return splashActivityComponent
    }

    fun clear() {
        Log.d()
        splashActivityComponent = null
    }
}