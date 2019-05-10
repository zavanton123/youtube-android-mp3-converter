package com.zavanton.yoump3.di.module

import android.content.Context
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.zavanton.yoump3.di.qualifier.context.ApplicationContext
import com.zavanton.yoump3.domain.model.FfmpegManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FfmpegModule {

    @Singleton
    @Provides
    fun provideFFMpeg(
        @ApplicationContext context: Context
    ): FFmpeg = FFmpeg.getInstance(context)

    @Singleton
    @Provides
    fun provideFfmpegManager(ffmpeg: FFmpeg): FfmpegManager = FfmpegManager(ffmpeg)
}