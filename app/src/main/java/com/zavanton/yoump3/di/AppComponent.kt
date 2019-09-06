package com.zavanton.yoump3.di

import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.core.di.*
import com.zavanton.yoump3.di.module.*
import com.zavanton.yoump3.domain.model.ConversionManager
import com.zavanton.yoump3.eventbus.EventBus
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

interface ConversionApi {

    fun provideFFMpeg(): FFmpeg
    fun provideFfmpegManager(): ConversionManager
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

interface EventBusApi {

    fun provideDownloadEventBus(): EventBus
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
        EventBusModule::class,
        NotificationModule::class,
        ConversionModule::class
    ]
)
interface AppComponent :
    AppApi,
    SchedulerApi,
    ClipboardApi,
    NetworkApi,
    EventBusApi,
    NotificationApi,
    ConversionApi {

    fun inject(theApp: TheApp)
}