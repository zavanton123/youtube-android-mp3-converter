package com.zavanton.yoump3.download.di

import android.content.Context
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.zavanton.yoump3.core.di.*
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.download.business.interactor.ConversionInteractor
import com.zavanton.yoump3.download.business.interactor.DownloadInteractor
import com.zavanton.yoump3.download.business.interactor.IConversionInteractor
import com.zavanton.yoump3.download.business.interactor.IDownloadInteractor
import com.zavanton.yoump3.download.data.ConversionService
import com.zavanton.yoump3.download.data.IConversionService
import com.zavanton.yoump3.download.data.IDownloadService
import com.zavanton.yoump3.download.eventBus.EventBus
import com.zavanton.yoump3.download.ui.presenter.DownloadServicePresenter
import com.zavanton.yoump3.download.ui.presenter.IDownloadServicePresenter
import com.zavanton.yoump3.download.ui.view.DownloadService
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides

interface EventBusApi {

    fun provideDownloadEventBus(): EventBus
}

@ServiceScope
@Component(
    modules = [
        DownloadServiceBindModule::class,
        ConversionModule::class,
        EventBusModule::class
    ],
    dependencies = [
        AppApi::class,
        SchedulerApi::class,
        NetworkApi::class,
        NotificationApi::class
    ]
)
interface DownloadServiceComponent : EventBusApi {

    fun inject(downloadService: DownloadService)
}

@Module
abstract class DownloadServiceBindModule {

    @Binds
    abstract fun bindPresenter(impl: DownloadServicePresenter): IDownloadServicePresenter

    @Binds
    abstract fun bindDownloadInteractor(impl: DownloadInteractor): IDownloadInteractor

    @Binds
    abstract fun bindDownloadService(impl: com.zavanton.yoump3.download.data.DownloadService): IDownloadService

    @Binds
    abstract fun bindConvertInteractor(impl: ConversionInteractor): IConversionInteractor

    @Binds
    abstract fun bindConversionService(impl: ConversionService): IConversionService
}

@Module
class ConversionModule {

    @ServiceScope
    @Provides
    fun provideFFMpeg(
        @ApplicationContext context: Context
    ): FFmpeg = FFmpeg.getInstance(context).apply {
        try {
            loadBinary(object : LoadBinaryResponseHandler() {
                override fun onStart() {
                }

                override fun onFailure() {
                }

                override fun onSuccess() {
                }

                override fun onFinish() {
                }
            })
        } catch (exception: FFmpegNotSupportedException) {
            Log.e(exception)
        }
    }
}

@Module
class EventBusModule {

    @ServiceScope
    @Provides
    fun provideDownloadEventBus(): EventBus = EventBus()
}