package com.zavanton.yoump3.di.manager

import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.di.component.AppComponent
import com.zavanton.yoump3.di.component.DaggerAppComponent
import com.zavanton.yoump3.di.module.AppModule

object ApplicationComponentManager {

    lateinit var appComponent: AppComponent

    fun inject(theApp: TheApp) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(theApp))
            .build()
            .apply {
                inject(theApp)
            }
    }
}