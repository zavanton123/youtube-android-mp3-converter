package com.zavanton.yoump3.ui.download.presenter

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.os.Environment
import com.zavanton.yoump3.di.qualifier.scheduler.IoThreadScheduler
import com.zavanton.yoump3.di.qualifier.scheduler.MainThreadScheduler
import com.zavanton.yoump3.di.scope.ServiceScope
import com.zavanton.yoump3.domain.interactor.convert.IConvertInteractor
import com.zavanton.yoump3.domain.interactor.download.IDownloadInteractor
import com.zavanton.yoump3.eventbus.DownloadEventBus
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
    private val downloadEventBus: DownloadEventBus
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

    @SuppressLint("StaticFieldLeak")
    private fun runTask() {

        val urlLink = clipboardManager.primaryClip?.getItemAt(0)?.text.toString()

        if (urlLink.isNotEmpty()) {

            compositeDisposable.add(downloadInteractor.downloadFile(
                urlLink,
                DOWNLOADS_FOLDER,
                TARGET_FILENAME,
                VIDEO_EXTENSION
            )
                .subscribeOn(ioThreadScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(
                    { onDownloadNextMessageReceive(it) },
                    { onDownloadError(it) }
                )
            )
        }
    }

    private fun onDownloadNextMessageReceive(message: String?) {
        Logger.d("onDownloadNextMessageReceive: $message")
        downloadEventBus.sendMessage("Is the file downloaded: $message")
        convert()
    }

    private fun onDownloadError(it: Throwable?) {
        Logger.e("onDownloadError - Some error while downloading", it)
        downloadEventBus.sendMessage("Some error while downloading")
        service?.stopForeground()
    }

    private fun convert() {
        compositeDisposable.add(convertInteractor.convertToMp3(
            "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION",
            "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$AUDIO_EXTENSION"
        )
            .subscribeOn(ioThreadScheduler)
            .observeOn(mainThreadScheduler)
            .subscribe(
                { onConvertNextMessageReceive(it) },
                { onConvertError(it) },
                { onConvertComplete() }
            ))
    }

    private fun onConvertNextMessageReceive(message: String) {
        Logger.d("onConvertNextMessageReceive: $message")
    }

    private fun onConvertError(it: Throwable?) {
        Logger.e("onConvertError - Some error while converting", it)
        service?.stopForeground()
    }

    private fun onConvertComplete() {
        Logger.d("onConvertComplete")
        downloadEventBus.sendMessage("The file is successfully converted!")
        service?.stopForeground()
    }
}