package com.zavanton.yoump3.app

import android.app.Application
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.zavanton.yoump3.di.component.AppComponent
import com.zavanton.yoump3.di.component.DaggerAppComponent
import com.zavanton.yoump3.di.module.AppModule
import com.zavanton.yoump3.utils.Logger

class TheApp : Application() {

    companion object {

        private lateinit var appComponent: AppComponent

        fun getAppComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()

        initAppComponent()
        initFfmpeg()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private fun initFfmpeg() {
        val ffmpeg = FFmpeg.getInstance(this)
        try {
            ffmpeg.loadBinary(object : LoadBinaryResponseHandler() {

                override fun onStart() {}

                override fun onFailure() {}

                override fun onSuccess() {
                    Logger.d("Succeeded to load FFMPEG")
                }

                override fun onFinish() {}
            })
        } catch (e: FFmpegNotSupportedException) {
            Logger.d("Failed to load FFMPEG")
        }
    }
}