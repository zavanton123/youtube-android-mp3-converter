package com.zavanton.yoump3.app

import android.app.NotificationManager
import android.os.Build
import androidx.multidex.MultiDexApplication
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.zavanton.yoump3.di.component.AppComponent
import com.zavanton.yoump3.di.component.DaggerAppComponent
import com.zavanton.yoump3.di.module.AppModule
import com.zavanton.yoump3.utils.Logger
import com.zavanton.yoump3.utils.NotificationChannels
import javax.inject.Inject

class TheApp : MultiDexApplication() {

    companion object {

        private lateinit var appComponent: AppComponent

        fun getAppComponent(): AppComponent = appComponent
    }

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()

        initAppComponent()
        initNotificationChannels()
        initFfmpeg()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .apply {
                inject(this@TheApp)
            }
    }

    private fun initNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannels(NotificationChannels.NOTIFICATION_CHANNELS)
        }
    }

    private fun initFfmpeg() {
        val ffmpeg = FFmpeg.getInstance(this)
        try {
            ffmpeg.loadBinary(object : LoadBinaryResponseHandler() {

                override fun onStart() {
                    Logger.d("FFmpeg - onStart")
                }

                override fun onFailure() {
                    Logger.d("FFmpeg - onFailure")
                }

                override fun onSuccess() {
                    Logger.d("FFmpeg - onSuccess")
                }

                override fun onFinish() {
                    Logger.d("FFmpeg - onFinish")
                }
            })
        } catch (e: FFmpegNotSupportedException) {
            Logger.e("FFmpeg - Error while initializing FFmpeg", e)
        }
    }
}