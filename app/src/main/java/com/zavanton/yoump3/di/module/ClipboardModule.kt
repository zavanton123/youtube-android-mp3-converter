package com.zavanton.yoump3.di.module

import android.content.ClipboardManager
import android.content.Context
import com.zavanton.yoump3.core.di.AppScope
import com.zavanton.yoump3.core.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ClipboardModule {

    @AppScope
    @Provides
    fun provideClipboardManager(
        @ApplicationContext
        context: Context
    ): ClipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
}