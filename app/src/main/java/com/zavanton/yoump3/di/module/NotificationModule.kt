package com.zavanton.yoump3.di.module

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.zavanton.yoump3.di.ApplicationContext
import com.zavanton.yoump3.di.HighNotificationChannel
import com.zavanton.yoump3.di.LowNotificationChannel
import com.zavanton.yoump3.di.NormalNotificationChannel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotificationModule {

    companion object {

        private const val LOW = "Low"
        private const val NORMAL = "Normal"
        private const val HIGH = "High"

        private const val NOTIFICATION_CHANNEL_LOW = "NOTIFICATION_CHANNEL_LOW"
        private const val NOTIFICATION_CHANNEL_NORMAL = "NOTIFICATION_CHANNEL_NORMAL"
        private const val NOTIFICATION_CHANNEL_HIGH = "NOTIFICATION_CHANNEL_HIGH"

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


    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext
        context: Context
    ): NotificationManager =
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannels(NOTIFICATION_CHANNELS)
                }
            }

    @Singleton
    @Provides
    @NormalNotificationChannel
    fun provideNotificationBuilderWithNormalNotificationChannel(
        @ApplicationContext
        context: Context
    ): NotificationCompat.Builder =
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_NORMAL)

    @Singleton
    @Provides
    @LowNotificationChannel
    fun provideNotificationBuilderWithLowNotificationChannel(
        @ApplicationContext
        context: Context
    ): NotificationCompat.Builder =
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_LOW)

    @Singleton
    @Provides
    @HighNotificationChannel
    fun provideNotificationBuilderWithHighNotificationChannel(
        @ApplicationContext
        context: Context
    ): NotificationCompat.Builder =
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_HIGH)
}