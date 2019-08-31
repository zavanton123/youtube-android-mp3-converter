package com.zavanton.yoump3.ui.main.activity.di.manager

import com.zavanton.yoump3.di.manager.ApplicationComponentManager
import com.zavanton.yoump3.ui.main.activity.di.component.MainActivityComponent
import com.zavanton.yoump3.ui.main.activity.di.module.MainActivityProvideModule
import com.zavanton.yoump3.ui.main.activity.view.MainActivityViewModel
import com.zavanton.yoump3.utils.Log

object MainActivityComponentManager {

    var mainActivityComponent: MainActivityComponent? = null

    fun inject(mainActivityViewModel: MainActivityViewModel) {
        Log.d()

        if (mainActivityComponent == null) {
            mainActivityComponent = ApplicationComponentManager.appComponent
                .plusMainActivityComponent(MainActivityProvideModule())
        }

        mainActivityComponent?.inject(mainActivityViewModel)
    }

    fun clear() {
        Log.d()
        mainActivityComponent = null
    }
}