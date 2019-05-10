package com.zavanton.yoump3.ui.download.presenter

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Environment
import com.zavanton.yoump3.di.qualifier.scheduler.IoThreadScheduler
import com.zavanton.yoump3.di.qualifier.scheduler.MainThreadScheduler
import com.zavanton.yoump3.di.scope.ServiceScope
import com.zavanton.yoump3.domain.interactor.convert.IConvertInteractor
import com.zavanton.yoump3.domain.interactor.download.IDownloadInteractor
import com.zavanton.yoump3.eventbus.Event
import com.zavanton.yoump3.eventbus.EventBus
import com.zavanton.yoump3.eventbus.Message
import com.zavanton.yoump3.ui.download.view.IDownloadService
import com.zavanton.yoump3.utils.Logger
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
    private val clipboardManager: ClipboardManager,
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

    override fun onStartCommand() {
        Logger.d("DownloadServicePresenter - onStartCommand")

        service?.startForeground()
        runTask()
    }

    override fun bind(downloadService: IDownloadService) {
        service = downloadService
    }

    override fun unbind(downloadService: IDownloadService) {
        service = null
        compositeDisposable.clear()
    }

    // TODO check if internet connection is ok
    private fun runTask() {
        Logger.d("runTask")
        val clipboardItem = clipboardManager.primaryClip?.getItemAt(0)
        checkClipboardAndProceed(clipboardItem)
    }

    private fun checkClipboardAndProceed(clipboardItem: ClipData.Item?) {
        Logger.d("checkClipboardAndProceed: $clipboardItem")
        if (clipboardItem != null) {
            eventBus.send(Message(Event.CLIPBOARD_NOT_EMPTY))
            checkUrlAndProceed(clipboardItem.text.toString())
        } else {
            eventBus.send(Message(Event.CLIPBOARD_EMPTY))
            service?.stopForeground()
        }
    }

    private fun checkUrlAndProceed(url: String) {
        Logger.d("checkUrlAndProceed: $url")
        if (isUrlValid(url)) {
            eventBus.send(Message(Event.URL_VALID))
            downloadFile(url)
        } else {
            eventBus.send(Message(Event.URL_INVALID))
            service?.stopForeground()
        }
    }

    // TODO add network request to the url to check if response is ok
    // TODO find a better way to check youtube video url
    private fun isUrlValid(url: String): Boolean {
        Logger.d("isUrlValid: $url")
        if (url.isEmpty()) return false

        if (url.contains("yout")) return true

        return false
    }

    private fun downloadFile(url: String) {
        Logger.d("downloadFile: $url")
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

    private fun onDownloadProgress(progressInPercent: String) {
        Logger.d("onDownloadProgress: $progressInPercent")
        eventBus.send(Message(Event.DOWNLOAD_PROGRESS, progressInPercent))
    }

    private fun onDownloadError(it: Throwable) {
        Logger.e("onDownloadError", it)
        eventBus.send(Message(Event.DOWNLOAD_ERROR))
        service?.stopForeground()
    }

    private fun onDownloadComplete() {
        Logger.d("onDownloadComplete")
        eventBus.send(Message(Event.DOWNLOAD_SUCCESS))
        convertToMp3()
    }

    private fun convertToMp3() {
        Logger.d("convertToMp3")
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

    private fun onConvertProgress(conversionProgress: String) {
        eventBus.send(Message(Event.CONVERSION_PROGRESS, conversionProgress))
        Logger.d("onConvertProgress: $conversionProgress")
    }

    private fun onConvertError(it: Throwable) {
        Logger.e("onConvertError", it)
        eventBus.send(Message(Event.CONVERSION_ERROR))
        service?.stopForeground()
    }

    private fun onConvertComplete() {
        Logger.d("onConvertComplete")
        eventBus.send(Message(Event.CONVERSION_SUCCESS))
        service?.stopForeground()
    }
}