package com.zavanton.yoump3.di.module

import android.content.ClipboardManager
import android.content.Context
import com.zavanton.yoump3.di.qualifier.context.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ClipboardModule {

    @Singleton
    @Provides
    fun provideClipboardManager(
        @ApplicationContext
        context: Context
    ): ClipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
}