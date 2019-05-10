package com.zavanton.yoump3.app

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import com.zavanton.yoump3.di.manager.ApplicationComponentManager
import com.zavanton.yoump3.domain.model.ConversionManager
import com.zavanton.yoump3.utils.NotificationChannels
import javax.inject.Inject

class TheApp : Application() {

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var conversionManager: ConversionManager

    override fun onCreate() {
        super.onCreate()

        initDependencies()
        initNotificationChannels()
        initFfmpeg()
    }

    private fun initDependencies() {
        ApplicationComponentManager.inject(this)
    }

    private fun initNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannels(NotificationChannels.NOTIFICATION_CHANNELS)
        }
    }

    private fun initFfmpeg() {
        conversionManager.init()
    }
}