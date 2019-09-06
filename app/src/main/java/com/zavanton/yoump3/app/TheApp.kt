package com.zavanton.yoump3.app

import android.app.Application
import com.zavanton.yoump3.di.AppComponentManager
import com.zavanton.yoump3.core.utils.Log

class TheApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d()

        AppComponentManager.inject(this)
    }
}