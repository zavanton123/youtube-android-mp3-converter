package com.zavanton.yoump3.app

import android.app.Application
import com.zavanton.yoump3.di.AppComponentManager
import com.zavanton.yoump3.domain.model.ConversionManager
import com.zavanton.yoump3.utils.Log
import javax.inject.Inject

class TheApp : Application() {

    @Inject
    lateinit var conversionManager: ConversionManager

    override fun onCreate() {
        super.onCreate()
        Log.d()

        initDependencies()
        initFfmpeg()
    }

    private fun initDependencies() {
        Log.d()
        AppComponentManager.inject(this)
    }

    private fun initFfmpeg() {
        Log.d()
        conversionManager.init()
    }
}