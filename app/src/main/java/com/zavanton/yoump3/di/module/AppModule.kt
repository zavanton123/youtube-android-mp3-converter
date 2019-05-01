package com.zavanton.yoump3.di.module

import android.content.Context
import com.zavanton.yoump3.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    @ApplicationContext
    fun provideApplicationContext(): Context = context
}