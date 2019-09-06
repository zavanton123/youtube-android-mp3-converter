package com.zavanton.yoump3.di

import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.di.module.AppModule

object AppComponentManager {

    private var appComponent: AppComponent? = null

    private lateinit var theApp: TheApp

    fun init(theApp: TheApp){
        this.theApp = theApp
    }

    fun getAppComponent(): AppComponent {
        Log.d()

        return appComponent ?: DaggerAppComponent.builder()
            .appModule(AppModule(theApp))
            .build()
            .also {
                appComponent = it
            }
    }
}