package com.zavanton.yoump3.ui.download.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.util.Log
import android.util.SparseArray
import androidx.core.app.NotificationCompat
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.di.qualifier.channel.NormalNotificationChannel
import com.zavanton.yoump3.ui.download.di.component.DownloadServiceComponent
import com.zavanton.yoump3.ui.download.di.module.DownloadServiceProvideModule
import com.zavanton.yoump3.ui.download.presenter.IDownloadPresenter
import com.zavanton.yoump3.ui.main.activity.MainActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DownloadService : Service() {

    companion object {

        val YOUTUBE_TAGS = arrayOf(37, 22, 18, 85, 84, 83, 82)
        private val TARGET_FILENAME = "Youtube-" + SimpleDateFormat("yyyy.MM.dd-HH-mm-ss", Locale.US).format(Date())
        private const val VIDEO_EXTENSION = "mp4"
        private const val AUDIO_EXTENSION = "mp3"
        private val DOWNLOADS_FOLDER =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath
        private const val FOREGROUND_NOTIFICATION_ID = 123
        private const val ACTIVITY_CODE = 234
    }

    @Inject
    lateinit var presenter: IDownloadPresenter

    @Inject
    @field:NormalNotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder

    private lateinit var downloadServiceComponent: DownloadServiceComponent

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        makeServiceForeground()
        runTask()

        presenter.onStartCommand()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun initDependencies() {
        downloadServiceComponent = TheApp.getAppComponent()
            .plusDownloadServiceComponent(DownloadServiceProvideModule())
            .apply {
                inject(this@DownloadService)
            }
    }


    private fun makeServiceForeground() {
        val activityIntent = Intent(this, MainActivity::class.java)
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, ACTIVITY_CODE, activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = notificationBuilder
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setContentTitle("YouMP3 is currently working")
            .setContentInfo("The YouMP3 service is running...")
            .setContentText("Downloading a Youtube video and converting it to mp3...")
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        // Starts foreground
        startForeground(FOREGROUND_NOTIFICATION_ID, notification)
    }

    @SuppressLint("StaticFieldLeak")
    private fun runTask() {

        val clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val urlLink = clipboardManager.primaryClip
            .getItemAt(0).text.toString()
        Log.d("zavanton", "urlLink: $urlLink")

        if (urlLink.isNotEmpty()) {

            object : YouTubeExtractor(this) {
                public override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
                    if (ytFiles != null) {

                        var youtubeFile: YtFile? = null
                        for (tag in YOUTUBE_TAGS) {
                            if (ytFiles[tag] != null) {
                                youtubeFile = ytFiles[tag]
                            }
                        }

                        val url = youtubeFile?.url
                        Log.d("zavantondebug", url)

                        doItInBackground(url ?: "")
                    }
                }
            }.extract(urlLink, true, true)
        } else {
            Log.d("zavantondebug", "The clipboard is empty!")
        }
    }

    private fun doItInBackground(url: String) {
        val handlerThread = HandlerThread("handlerThread")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        handler.post {

            val filename = "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION"
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
        Log.d("zavantondebug", "response closed")
    }

    private fun InputStream.toFile(path: String) {
        File(path).outputStream().use { this.copyTo(it) }
    }

    private fun convertToMp3() {
        val ffmpeg = FFmpeg.getInstance(this)
        try {
            val videoFile = "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION"
            Log.d("zavantondebug", "videofile: $videoFile")

            val audioFile = "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$AUDIO_EXTENSION"
            Log.d("zavantondebug", "audiofile: $audioFile")

            val commands = arrayOf("-i", videoFile, audioFile, "-b:a 192K -vn", audioFile)
            ffmpeg.execute(commands, object : ExecuteBinaryResponseHandler() {

                override fun onStart() {
                    Log.d("zavantondebug", "onStart")
                }

                override fun onProgress(message: String?) {
                    Log.d("zavantondebug", "onProgress: $message")
                }

                override fun onFailure(message: String?) {
                    Log.d("zavantondebug", "onFailure: $message")
                }

                override fun onSuccess(message: String?) {
                    Log.d("zavantondebug", "onSuccess: $message")
                }

                override fun onFinish() {
                    Log.d("zavantondebug", "onFinish")
                    File("$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION").delete()
                    stopForeground(true)
                }
            })
        } catch (e: FFmpegCommandAlreadyRunningException) {
            Log.e("zavantondebug", "Failed to convert", e)
        }
    }
}
