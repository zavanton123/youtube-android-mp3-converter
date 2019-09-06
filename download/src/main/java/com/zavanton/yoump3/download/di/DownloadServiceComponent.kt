package com.zavanton.yoump3.download.di

import com.zavanton.yoump3.core.di.*
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

@ServiceScope
@Component(
    modules = [
        DownloadServiceProvideModule::class,
        DownloadServiceBindModule::class
    ],
    dependencies = [
        AppApi::class,
        SchedulerApi::class,
        ClipboardApi::class,
        NetworkApi::class,
        EventBusApi::class,
        NotificationApi::class,
        ConversionApi::class
    ]
)
interface DownloadServiceComponent {

    fun inject(downloadService: DownloadService)
}

@Module
class DownloadServiceProvideModule

@Module
abstract class DownloadServiceBindModule {

    @Binds
    abstract fun bindPresenter(presenter: DownloadServicePresenter): IDownloadServicePresenter

    @Binds
    abstract fun bindDownloadInteractor(interactor: DownloadInteractor): IDownloadInteractor

    @Binds
    abstract fun bindConvertInteractor(interactor: ConvertInteractor): IConvertInteractor
}