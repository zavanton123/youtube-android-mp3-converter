package com.zavanton.yoump3.di

import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.di.module.AppModule
import com.zavanton.yoump3.core.utils.Log

object AppComponentManager {

    lateinit var appComponent: AppComponent

    fun inject(theApp: TheApp) {
        Log.d()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(theApp))
            .build()
    }
}