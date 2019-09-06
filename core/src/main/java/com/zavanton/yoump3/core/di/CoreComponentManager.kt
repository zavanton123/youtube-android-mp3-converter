package com.zavanton.yoump3.core.di

import android.annotation.SuppressLint
import android.content.Context
import com.zavanton.yoump3.core.di.module.AppModule

@SuppressLint("StaticFieldLeak")
object CoreComponentManager {

    private lateinit var context: Context
    private var coreComponent: CoreComponent? = null

    fun init(context: Context) {
        this.context = context
    }

    fun getCoreComponent(): CoreComponent {
        return coreComponent ?: DaggerCoreComponent
            .builder()
            .appModule(AppModule(context))
            .build()
            .also {
                coreComponent = it
            }
    }

    fun clearCoreComponent() {
        coreComponent = null
    }

}