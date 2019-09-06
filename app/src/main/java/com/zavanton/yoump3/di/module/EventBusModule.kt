package com.zavanton.yoump3.di.module

import com.zavanton.yoump3.core.di.AppScope
import com.zavanton.yoump3.eventbus.EventBus
import dagger.Module
import dagger.Provides

@Module
class EventBusModule {

    @AppScope
    @Provides
    fun provideDownloadEventBus(): EventBus = EventBus()
}