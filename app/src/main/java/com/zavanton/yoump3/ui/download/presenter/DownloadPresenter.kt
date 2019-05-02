package com.zavanton.yoump3.ui.download.presenter

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.SparseArray
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.zavanton.yoump3.di.qualifier.context.ApplicationContext
import com.zavanton.yoump3.di.scope.ServiceScope
import com.zavanton.yoump3.ui.download.service.IDownloadService
import com.zavanton.yoump3.utils.Logger
import com.zavanton.yoump3.utils.YoutubeTags
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ServiceScope
class DownloadPresenter
@Inject
constructor(
    @ApplicationContext
    private val appContext: Context
) : IDownloadPresenter {

    companion object {

        private val TARGET_FILENAME = "Youtube-" + SimpleDateFormat("yyyy.MM.dd-HH-mm-ss", Locale.US).format(Date())
        private const val VIDEO_EXTENSION = "mp4"
        private const val AUDIO_EXTENSION = "mp3"
        private val DOWNLOADS_FOLDER =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
    }

    private var service: IDownloadService? = null

    init {
        Logger.d("DownloadPresenter is init")
    }

    override fun bind(downloadService: IDownloadService) {
        service = downloadService
    }

    override fun unbind(downloadService: IDownloadService) {
        service = null
    }

    override fun onStartCommand() {
        Logger.d("DownloadPresenter - onStartCommand")

        service?.startForeground()
        runTask()
    }

    @SuppressLint("StaticFieldLeak")
    private fun runTask() {

        val clipboardManager: ClipboardManager =
            appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val urlLink = clipboardManager.primaryClip?.getItemAt(0)?.text.toString()

        if (urlLink.isNotEmpty()) {

            object : YouTubeExtractor(appContext) {

                public override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
                    if (ytFiles != null) {

                        var youtubeFile: YtFile? = null
                        for (tag in YoutubeTags.MP4) {

                            if (ytFiles[tag] != null) {
                                youtubeFile = ytFiles[tag]
                            }
                        }

                        val url = youtubeFile?.url

                        doItInBackground(url ?: "")
                    }
                }
            }.extract(urlLink, true, true)
        } else {
            Logger.d("The clipboard is empty!")
        }
    }

    private fun doItInBackground(url: String) {
        val handlerThread = HandlerThread("handlerThread")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        handler.post {

            val filename =
                "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION"
            downloadFile(url, filename)
        }
    }

    private fun downloadFile(url: String, filename: String) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url)
            .build()
        val response = client.newCall(request).execute()

        val inputStream = response.body()?.byteStream()

        inputStream?.toFile(filename)

        convertToMp3()

        response.body()?.close()
        Logger.d("response closed")
    }

    private fun InputStream.toFile(path: String) {
        File(path).outputStream().use { this.copyTo(it) }
    }

    private fun convertToMp3() {
        val ffmpeg = FFmpeg.getInstance(appContext)
        try {
            val videoFile = "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION"

            val audioFile = "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$AUDIO_EXTENSION"

            val commands = arrayOf("-i", videoFile, audioFile)

            ffmpeg.execute(commands, object : ExecuteBinaryResponseHandler() {

                override fun onStart() {
                    Logger.d("onStart")
                }

                override fun onProgress(message: String?) {
                    Logger.d("onProgress: $message")
                }

                override fun onFailure(message: String?) {
                    Logger.d("onFailure: $message")
                }

                override fun onSuccess(message: String?) {
                    Logger.d("onSuccess: $message")
                }

                override fun onFinish() {
                    Logger.d("onFinish")
                    File("$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION").delete()
                    service?.stopForeground()
                }
            })
        } catch (e: FFmpegCommandAlreadyRunningException) {
            Logger.e("FFmpegCommandAlreadyRunningException", e)
        }
    }
}