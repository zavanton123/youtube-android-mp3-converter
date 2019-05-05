package com.zavanton.yoump3.di.module

import com.zavanton.yoump3.eventbus.DownloadEventBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EventBusModule {

    @Singleton
    @Provides
    fun provideDownloadEventBus(): DownloadEventBus = DownloadEventBus()
}