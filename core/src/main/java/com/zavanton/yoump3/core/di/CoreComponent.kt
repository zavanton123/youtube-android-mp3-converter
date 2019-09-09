package com.zavanton.yoump3.core.di

import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.zavanton.yoump3.core.di.module.*
import dagger.Component
import io.reactivex.Scheduler
import okhttp3.OkHttpClient

interface AppApi {

    @ApplicationContext
    fun provideApplicationContext(): Context
}

interface SchedulerApi {

    @MainThreadScheduler
    fun provideMainThreadScheduler(): Scheduler

    @IoThreadScheduler
    fun provideIoThreadScheduler(): Scheduler
}

interface ClipboardApi {

    fun provideClipboardManager(): ClipboardManager
}

interface NotificationApi {

    fun provideNotificationManager(): NotificationManager

    @NormalNotificationChannel
    fun provideNotificationBuilderWithNormalNotificationChannel(): NotificationCompat.Builder

    @LowNotificationChannel
    fun provideNotificationBuilderWithLowNotificationChannel(): NotificationCompat.Builder

    @HighNotificationChannel
    fun provideNotificationBuilderWithHighNotificationChannel(): NotificationCompat.Builder
}

interface NetworkApi {

    fun provideClient(): OkHttpClient
}

@AppScope
@Component(
    modules = [
        AppModule::class,
        SchedulerModule::class,
        ClipboardModule::class,
        NetworkModule::class,
        NotificationModule::class
    ]
)
interface CoreComponent :
    AppApi,
    SchedulerApi,
    ClipboardApi,
    NetworkApi,
    NotificationApi