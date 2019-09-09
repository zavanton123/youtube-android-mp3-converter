package com.zavanton.yoump3.download.ui.presenter

import android.os.Environment
import com.zavanton.yoump3.core.di.IoThreadScheduler
import com.zavanton.yoump3.core.di.MainThreadScheduler
import com.zavanton.yoump3.core.di.ServiceScope
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.download.business.interactor.IDownloadInteractor
import com.zavanton.yoump3.download.business.model.Event
import com.zavanton.yoump3.download.eventBus.EventBus
import com.zavanton.yoump3.download.ui.view.IDownloadService
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ServiceScope
class DownloadServicePresenter @Inject constructor(
    @MainThreadScheduler
    private val mainThreadScheduler: Scheduler,
    @IoThreadScheduler
    private val ioThreadScheduler: Scheduler,
    private val downloadInteractor: IDownloadInteractor,
    private val eventBus: EventBus
) : IDownloadServicePresenter {

    companion object {

        private val TARGET_FILENAME = "Youtube-" + SimpleDateFormat("yyyy.MM.dd-HH-mm-ss", Locale.US).format(Date())
        private const val VIDEO_EXTENSION = "mp4"
        private const val AUDIO_EXTENSION = "mp3"
        private val DOWNLOADS_FOLDER =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
    }

    private var service: IDownloadService? = null

    private val compositeDisposable = CompositeDisposable()
    private val eventBusDisposable = CompositeDisposable()

    override fun onStartCommand() {
        eventBusDisposable.add(
            eventBus.listen()
                .subscribe {
                    processEvent(it)
                }
        )
    }

    override fun bind(downloadService: IDownloadService) {
        Log.d("downloadService: $downloadService")
        service = downloadService
    }

    override fun unbind(downloadService: IDownloadService) {
        Log.d("downloadService: $downloadService")
        service = null
        compositeDisposable.clear()
        eventBusDisposable.clear()
    }

    private fun processEvent(event: Event) {
        Log.i("$event")
        if (event is Event.SendDownloadUrl) {
            downloadAndConvert(event.url)
        } else {
            Log.i("Other event received")
        }
    }

    // TODO check if internet connection is ok
    private fun downloadAndConvert(url: String?) {
        Log.d("url: $url")
        service?.startForeground()

        url?.let {
            downloadFile(it)
        }
    }

    private fun downloadFile(url: String) {
        Log.i("url: $url")

        eventBus.send(Event.DownloadStarted)

        compositeDisposable.add(downloadInteractor.downloadFile(
            url,
            DOWNLOADS_FOLDER,
            TARGET_FILENAME,
            VIDEO_EXTENSION,
            "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION",
            "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$AUDIO_EXTENSION"
        )
            .subscribeOn(ioThreadScheduler)
            .observeOn(mainThreadScheduler)
            .subscribe(
                { onDownloadProgress(it) },
                { onDownloadError(it) },
                { onDownloadComplete() }
            )
        )
    }

    private fun onDownloadProgress(event: Event) {
        Log.d("event: $event")
        eventBus.send(event)
    }

    private fun onDownloadError(error: Throwable) {
        Log.e(error)
        eventBus.send(Event.DownloadError)
        service?.stopForeground()
    }

    private fun onDownloadComplete() {
        Log.d()
        eventBus.send(Event.DownloadSuccess)
    }
}