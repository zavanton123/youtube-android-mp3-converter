package com.zavanton.yoump3.ui.main.activity.di.manager

import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.ui.main.activity.di.component.MainActivityComponent
import com.zavanton.yoump3.ui.main.activity.di.module.MainActivityProvideModule
import com.zavanton.yoump3.ui.main.activity.view.MainActivityViewModel
import com.zavanton.yoump3.utils.Logger

object MainActivityComponentManager {

    var mainActivityComponent: MainActivityComponent? = null

    fun inject(mainActivityViewModel: MainActivityViewModel) {
        Logger.d("MainActivityComponentManager - inject")

        if (mainActivityComponent == null) {
            mainActivityComponent = TheApp.getAppComponent()
                .plusMainActivityComponent(MainActivityProvideModule())
        }

        mainActivityComponent?.inject(mainActivityViewModel)
    }

    fun clear() {
        Logger.d("MainActivityComponentManager - clear")
        mainActivityComponent = null
    }
}