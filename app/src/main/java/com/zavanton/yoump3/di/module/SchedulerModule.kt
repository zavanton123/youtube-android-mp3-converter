package com.zavanton.yoump3.di.module

import com.zavanton.yoump3.di.IoThreadScheduler
import com.zavanton.yoump3.di.MainThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Singleton
    @Provides
    @MainThreadScheduler
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Singleton
    @Provides
    @IoThreadScheduler
    fun provideIoThreadScheduler(): Scheduler = Schedulers.io()
}