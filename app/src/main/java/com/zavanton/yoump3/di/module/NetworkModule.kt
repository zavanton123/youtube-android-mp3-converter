package com.zavanton.yoump3.di.module

import com.zavanton.yoump3.core.di.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class NetworkModule {

    @AppScope
    @Provides
    fun provideClient(): OkHttpClient = OkHttpClient()
}