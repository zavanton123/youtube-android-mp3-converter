package com.zavanton.yoump3.core.di.module

import com.zavanton.yoump3.core.di.AppScope
import com.zavanton.yoump3.core.di.IoThreadScheduler
import com.zavanton.yoump3.core.di.MainThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class SchedulerModule {

    @AppScope
    @Provides
    @MainThreadScheduler
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @AppScope
    @Provides
    @IoThreadScheduler
    fun provideIoThreadScheduler(): Scheduler = Schedulers.io()
}