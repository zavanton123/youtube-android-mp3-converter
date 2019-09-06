package com.zavanton.yoump3.di.module

import android.content.Context
import com.zavanton.yoump3.core.di.AppScope
import com.zavanton.yoump3.core.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @AppScope
    @Provides
    @ApplicationContext
    fun provideApplicationContext(): Context = context
}