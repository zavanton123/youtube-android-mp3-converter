package com.zavanton.yoump3.download.di

import android.content.Context
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.zavanton.yoump3.core.di.*
import com.zavanton.yoump3.download.interactor.convert.ConversionService
import com.zavanton.yoump3.download.interactor.convert.ConvertInteractor
import com.zavanton.yoump3.download.interactor.convert.IConvertInteractor
import com.zavanton.yoump3.download.interactor.download.DownloadInteractor
import com.zavanton.yoump3.download.interactor.download.IDownloadInteractor
import com.zavanton.yoump3.download.ui.presenter.DownloadServicePresenter
import com.zavanton.yoump3.download.ui.presenter.IDownloadServicePresenter
import com.zavanton.yoump3.download.ui.view.DownloadService
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides

@ServiceScope
@Component(
    modules = [
        DownloadServiceBindModule::class,
        ConversionModule::class
    ],
    dependencies = [
        AppApi::class,
        SchedulerApi::class,
        ClipboardApi::class,
        NetworkApi::class,
        EventBusApi::class,
        NotificationApi::class
    ]
)
interface DownloadServiceComponent {

    fun inject(downloadService: DownloadService)
}

@Module
abstract class DownloadServiceBindModule {

    @Binds
    abstract fun bindPresenter(presenter: DownloadServicePresenter): IDownloadServicePresenter

    @Binds
    abstract fun bindDownloadInteractor(interactor: DownloadInteractor): IDownloadInteractor

    @Binds
    abstract fun bindConvertInteractor(interactor: ConvertInteractor): IConvertInteractor
}

@Module
class ConversionModule {

    @ServiceScope
    @Provides
    fun provideFFMpeg(
        @ApplicationContext context: Context
    ): FFmpeg = FFmpeg.getInstance(context)

    @ServiceScope
    @Provides
    fun provideFfmpegManager(ffmpeg: FFmpeg): ConversionService =
        ConversionService(ffmpeg).apply {
            init()
        }
}