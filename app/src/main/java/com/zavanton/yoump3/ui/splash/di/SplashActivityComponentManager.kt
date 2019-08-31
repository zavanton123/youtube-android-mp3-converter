package com.zavanton.yoump3.ui.splash.di

import com.zavanton.yoump3.di.AppComponentManager
import com.zavanton.yoump3.ui.splash.view.SplashActivityViewModel
import com.zavanton.yoump3.utils.Log

object SplashActivityComponentManager {

    private var splashActivityComponent: SplashActivityComponent? = null

    fun inject(splashActivityViewModel: SplashActivityViewModel) {
        Log.d()
        if (splashActivityComponent == null) {
            splashActivityComponent = AppComponentManager.appComponent
                .plusSplashActivityComponent(SplashActivityProvideModule())
        }
        splashActivityComponent?.inject(splashActivityViewModel)
    }

    fun clear() {
        Log.d()
        splashActivityComponent = null
    }
}