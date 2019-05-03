package com.zavanton.yoump3.ui.download.presenter

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.os.Environment
import com.zavanton.yoump3.di.qualifier.context.ApplicationContext
import com.zavanton.yoump3.di.scope.ServiceScope
import com.zavanton.yoump3.domain.interactor.IConvertInteractor
import com.zavanton.yoump3.domain.interactor.IDownloadInteractor
import com.zavanton.yoump3.ui.download.service.IDownloadService
import com.zavanton.yoump3.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ServiceScope
class DownloadPresenter
@Inject
constructor(
    @ApplicationContext
    private val appContext: Context,
    private val downloadInteractor: IDownloadInteractor,
    private val convertInteractor: IConvertInteractor
) : IDownloadPresenter {

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
        Logger.d("DownloadPresenter - onStartCommand")

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

        val clipboardManager: ClipboardManager =
            appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val urlLink = clipboardManager.primaryClip?.getItemAt(0)?.text.toString()

        if (urlLink.isNotEmpty()) {

            compositeDisposable.add(downloadInteractor.downloadFile(
                urlLink,
                DOWNLOADS_FOLDER,
                TARGET_FILENAME,
                VIDEO_EXTENSION
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Logger.d("Is download OK? - $it")
                        service?.stopForeground()
                    },
                    {
                        Logger.e("Some error while downloading", it)
                        service?.stopForeground()
                    }
                )
            )
        }
    }
}