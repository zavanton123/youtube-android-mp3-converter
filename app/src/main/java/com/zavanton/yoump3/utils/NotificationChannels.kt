package com.zavanton.yoump3.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

object NotificationChannels {

    private const val LOW = "Low"
    private const val NORMAL = "Normal"
    private const val HIGH = "High"

    const val NOTIFICATION_CHANNEL_LOW = "NOTIFICATION_CHANNEL_LOW"
    const val NOTIFICATION_CHANNEL_NORMAL = "NOTIFICATION_CHANNEL_NORMAL"
    const val NOTIFICATION_CHANNEL_HIGH = "NOTIFICATION_CHANNEL_HIGH"

    @RequiresApi(Build.VERSION_CODES.O)
    val normalChannel = NotificationChannel(
        NOTIFICATION_CHANNEL_NORMAL,
        NORMAL,
        NotificationManager.IMPORTANCE_DEFAULT
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val lowChannel = NotificationChannel(
        NOTIFICATION_CHANNEL_LOW,
        LOW,
        NotificationManager.IMPORTANCE_LOW
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val highChannel = NotificationChannel(
        NOTIFICATION_CHANNEL_HIGH,
        HIGH,
        NotificationManager.IMPORTANCE_HIGH
    )

    val NOTIFICATION_CHANNELS = listOf(
        lowChannel,
        normalChannel,
        highChannel
    )
}