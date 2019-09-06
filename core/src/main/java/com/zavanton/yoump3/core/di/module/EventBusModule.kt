package com.zavanton.yoump3.core.di.module

import com.zavanton.yoump3.core.eventBus.EventBus
import com.zavanton.yoump3.core.di.AppScope
import dagger.Module
import dagger.Provides

@Module
class EventBusModule {

    @AppScope
    @Provides
    fun provideDownloadEventBus(): EventBus =
        EventBus()
}