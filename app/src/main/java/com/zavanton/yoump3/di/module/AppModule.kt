package com.zavanton.yoump3.di.module

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.zavanton.yoump3.di.qualifier.channel.HighNotificationChannel
import com.zavanton.yoump3.di.qualifier.channel.LowNotificationChannel
import com.zavanton.yoump3.di.qualifier.channel.NormalNotificationChannel
import com.zavanton.yoump3.di.qualifier.context.ApplicationContext
import com.zavanton.yoump3.utils.NotificationChannels.NOTIFICATION_CHANNEL_HIGH
import com.zavanton.yoump3.utils.NotificationChannels.NOTIFICATION_CHANNEL_LOW
import com.zavanton.yoump3.utils.NotificationChannels.NOTIFICATION_CHANNEL_NORMAL
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    @ApplicationContext
    fun provideApplicationContext(): Context = context

    @Singleton
    @Provides
    fun provideNotificationManager(): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Singleton
    @Provides
    @NormalNotificationChannel
    fun provideNotificationBuilderWithNormalNotificationChannel(): NotificationCompat.Builder =
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_NORMAL)

    @Singleton
    @Provides
    @LowNotificationChannel
    fun provideNotificationBuilderWithLowNotificationChannel(): NotificationCompat.Builder =
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_LOW)

    @Singleton
    @Provides
    @HighNotificationChannel
    fun provideNotificationBuilderWithHighNotificationChannel(): NotificationCompat.Builder =
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_HIGH)
}