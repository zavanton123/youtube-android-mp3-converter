package com.zavanton.yoump3.ui.splash.di.manager

import com.zavanton.yoump3.di.manager.ApplicationComponentManager
import com.zavanton.yoump3.ui.splash.di.component.SplashActivityComponent
import com.zavanton.yoump3.ui.splash.di.module.SplashActivityProvideModule
import com.zavanton.yoump3.ui.splash.view.SplashActivityViewModel
import com.zavanton.yoump3.utils.Log

object SplashActivityComponentManager {

    private var splashActivityComponent: SplashActivityComponent? = null

    fun inject(splashActivityViewModel: SplashActivityViewModel) {
        Log.d()
        if (splashActivityComponent == null) {
            splashActivityComponent = ApplicationComponentManager.appComponent
                .plusSplashActivityComponent(SplashActivityProvideModule())
        }
        splashActivityComponent?.inject(splashActivityViewModel)
    }

    fun clear() {
        Log.d()
        splashActivityComponent = null
    }
}