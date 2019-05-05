package com.zavanton.yoump3.app

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.zavanton.yoump3.di.manager.ApplicationComponentManager
import com.zavanton.yoump3.utils.Logger
import com.zavanton.yoump3.utils.NotificationChannels
import javax.inject.Inject

class TheApp : Application() {

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var ffmpeg: FFmpeg

    override fun onCreate() {
        super.onCreate()

        ApplicationComponentManager.inject(this)
        initNotificationChannels()
        initFfmpeg()
    }

    private fun initNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannels(NotificationChannels.NOTIFICATION_CHANNELS)
        }
    }

    private fun initFfmpeg() {
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
        } catch (exception: FFmpegNotSupportedException) {
            Logger.e("FFmpeg - Error while initializing FFmpeg", exception)
        }
    }
}