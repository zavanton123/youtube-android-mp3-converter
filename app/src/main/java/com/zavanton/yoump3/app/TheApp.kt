package com.zavanton.yoump3.app

import android.app.Application
import com.zavanton.yoump3.core.di.CoreComponentManager
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.di.AppComponentManager

class TheApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d()

        CoreComponentManager.init(this)
    }
}