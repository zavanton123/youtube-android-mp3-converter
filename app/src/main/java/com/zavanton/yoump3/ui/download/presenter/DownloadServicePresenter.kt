package com.zavanton.yoump3.ui.download.presenter

import android.os.Environment
import com.zavanton.yoump3.di.IoThreadScheduler
import com.zavanton.yoump3.di.MainThreadScheduler
import com.zavanton.yoump3.di.ServiceScope
import com.zavanton.yoump3.domain.interactor.convert.IConvertInteractor
import com.zavanton.yoump3.domain.interactor.download.IDownloadInteractor
import com.zavanton.yoump3.eventbus.Event
import com.zavanton.yoump3.eventbus.EventBus
import com.zavanton.yoump3.eventbus.Message
import com.zavanton.yoump3.ui.download.view.IDownloadService
import com.zavanton.yoump3.utils.Log
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ServiceScope
class DownloadServicePresenter
@Inject
constructor(
    @MainThreadScheduler
    private val mainThreadScheduler: Scheduler,
    @IoThreadScheduler
    private val ioThreadScheduler: Scheduler,

    private val downloadInteractor: IDownloadInteractor,
    private val convertInteractor: IConvertInteractor,
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

    init {
        Log.d()
    }

    override fun onStartCommand() {
        Log.d()

        listenForMessages()
    }

    private fun listenForMessages() {
        Log.i()
        eventBusDisposable.add(eventBus.listenForMessages()
            .subscribe {
                processMessage(it)
            }
        )
    }

    private fun processMessage(message: Message) {
        Log.i("$message")
        when (message.event) {
            Event.DOWNLOAD_URL -> downloadAndConvert(message.text)
            else -> Log.i("Other event received")
        }
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

        Log.i("${Message(Event.DOWNLOAD_STARTED)}")
        eventBus.send(Message(Event.DOWNLOAD_STARTED))

        compositeDisposable.add(downloadInteractor.downloadFile(
            url,
            DOWNLOADS_FOLDER,
            TARGET_FILENAME,
            VIDEO_EXTENSION
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

    private fun onDownloadProgress(progress: Int) {
        Log.d("progress: $progress")

        Log.i("Message(Event.DOWNLOAD_PROGRESS, progress)")
        eventBus.send(Message(Event.DOWNLOAD_PROGRESS, progress.toString()))
    }

    private fun onDownloadError(error: Throwable) {
        Log.e(error)
        eventBus.send(Message(Event.DOWNLOAD_ERROR))
        service?.stopForeground()
    }

    private fun onDownloadComplete() {
        Log.d()

        Log.i("${Message(Event.DOWNLOAD_SUCCESS)}")
        eventBus.send(Message(Event.DOWNLOAD_SUCCESS))

        convertToMp3()
    }

    private fun convertToMp3() {
        Log.d()

        Log.i("${Message(Event.CONVERSION_STARTED)}")
        eventBus.send(Message(Event.CONVERSION_STARTED))

        compositeDisposable.add(convertInteractor.convertToMp3(
            "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION",
            "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$AUDIO_EXTENSION"
        )
            .subscribeOn(ioThreadScheduler)
            .observeOn(mainThreadScheduler)
            .subscribe(
                { onConvertProgress(it) },
                { onConvertError(it) },
                { onConvertComplete() }
            ))
    }

    private fun onConvertProgress(progress: String) {
        Log.d("progress: $progress")

        Log.i("${Message(Event.CONVERSION_PROGRESS, progress)}")
        eventBus.send(Message(Event.CONVERSION_PROGRESS, progress))
    }

    private fun onConvertError(error: Throwable) {
        Log.e(error)
        eventBus.send(Message(Event.CONVERSION_ERROR))
        service?.stopForeground()
    }

    private fun onConvertComplete() {
        Log.d()

        Log.i("${Message(Event.CONVERSION_SUCCESS)}")
        eventBus.send(Message(Event.CONVERSION_SUCCESS))
        service?.stopForeground()
    }
}