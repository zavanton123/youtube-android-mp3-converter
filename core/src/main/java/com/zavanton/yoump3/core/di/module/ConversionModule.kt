package com.zavanton.yoump3.core.di.module

import android.content.Context
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.zavanton.yoump3.core.business.ConversionManager
import com.zavanton.yoump3.core.di.AppScope
import com.zavanton.yoump3.core.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ConversionModule {

    @AppScope
    @Provides
    fun provideFFMpeg(
        @ApplicationContext context: Context
    ): FFmpeg = FFmpeg.getInstance(context)

    @AppScope
    @Provides
    fun provideFfmpegManager(ffmpeg: FFmpeg): ConversionManager =
        ConversionManager(ffmpeg).apply {
            init()
        }
}