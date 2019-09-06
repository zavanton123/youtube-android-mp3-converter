package com.zavanton.yoump3.di

import com.zavanton.yoump3.core.utils.Log

object AppComponentManager {

    private var appComponent: AppComponent? = null

    fun getAppComponent(): AppComponent {
        Log.d()

        return appComponent ?: DaggerAppComponent.builder()
            .build()
            .also {
                appComponent = it
            }
    }
}