package com.zavanton.yoump3.app

import android.app.Application
import com.zavanton.yoump3.di.AppComponentManager
import com.zavanton.yoump3.utils.Log

class TheApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d()

        initDependencies()
    }

    private fun initDependencies() {
        Log.d()
        AppComponentManager.inject(this)
    }
}