package com.zavanton.yoump3.di

object AppComponentManager {

    private var appComponent: AppComponent? = null

    fun getAppComponent(): AppComponent =
        appComponent ?: DaggerAppComponent.builder()
            .build()
            .also {
                appComponent = it
            }

    fun clearAppComponent() {
        appComponent = null
    }
}