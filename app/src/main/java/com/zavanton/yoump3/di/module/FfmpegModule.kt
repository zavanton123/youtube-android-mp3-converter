package com.zavanton.yoump3.di.module

import android.content.Context
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.zavanton.yoump3.di.qualifier.context.ApplicationContext
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
}