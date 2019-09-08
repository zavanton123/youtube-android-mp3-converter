package com.zavanton.yoump3.core.di

import android.annotation.SuppressLint
import android.content.Context
import com.zavanton.yoump3.core.di.module.AppModule

@SuppressLint("StaticFieldLeak")
object CoreComponentManager {

    private lateinit var appContext: Context
    private var coreComponent: CoreComponent? = null

    fun init(context: Context) {
        appContext = context
    }

    fun getCoreComponent(): CoreComponent =
        coreComponent ?: DaggerCoreComponent
            .builder()
            .appModule(AppModule(appContext))
            .build()
            .also {
                coreComponent = it
            }

    fun clearCoreComponent() {
        coreComponent = null
    }
}