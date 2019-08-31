package com.zavanton.yoump3.di.module

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.zavanton.yoump3.di.ApplicationContext
import com.zavanton.yoump3.di.HighNotificationChannel
import com.zavanton.yoump3.di.LowNotificationChannel
import com.zavanton.yoump3.di.NormalNotificationChannel
import com.zavanton.yoump3.utils.NotificationChannels
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotificationModule {

    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext
        context: Context
    ): NotificationManager =
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannels(NotificationChannels.NOTIFICATION_CHANNELS)
                }
            }

    @Singleton
    @Provides
    @NormalNotificationChannel
    fun provideNotificationBuilderWithNormalNotificationChannel(
        @ApplicationContext
        context: Context
    ): NotificationCompat.Builder =
        NotificationCompat.Builder(context, NotificationChannels.NOTIFICATION_CHANNEL_NORMAL)

    @Singleton
    @Provides
    @LowNotificationChannel
    fun provideNotificationBuilderWithLowNotificationChannel(
        @ApplicationContext
        context: Context
    ): NotificationCompat.Builder =
        NotificationCompat.Builder(context, NotificationChannels.NOTIFICATION_CHANNEL_LOW)

    @Singleton
    @Provides
    @HighNotificationChannel
    fun provideNotificationBuilderWithHighNotificationChannel(
        @ApplicationContext
        context: Context
    ): NotificationCompat.Builder =
        NotificationCompat.Builder(context, NotificationChannels.NOTIFICATION_CHANNEL_HIGH)
}