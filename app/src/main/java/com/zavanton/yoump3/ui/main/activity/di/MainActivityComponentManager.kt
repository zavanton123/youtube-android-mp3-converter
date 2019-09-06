package com.zavanton.yoump3.ui.main.activity.di

import com.zavanton.yoump3.di.AppComponentManager
import com.zavanton.yoump3.ui.main.activity.view.MainActivityViewModel
import com.zavanton.yoump3.utils.Log

object MainActivityComponentManager {

    var mainActivityComponent: MainActivityComponent? = null

    fun inject(mainActivityViewModel: MainActivityViewModel) {
        Log.d()

        if (mainActivityComponent == null) {
            mainActivityComponent = AppComponentManager.appComponent
                .plusMainActivityComponent(MainActivityProvideModule())
        }

        mainActivityComponent?.inject(mainActivityViewModel)
    }

    fun clear() {
        Log.d()
        mainActivityComponent = null
    }
}