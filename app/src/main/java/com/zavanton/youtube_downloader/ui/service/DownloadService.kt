package com.zavanton.youtube_downloader.ui.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.util.SparseArray
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.zavanton.youtube_downloader.ui.activity.MainActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.InputStream


class DownloadService : Service() {

    companion object {

        private const val LINK = "https://www.youtube.com/watch?v=IGQBtbKSVhY"
        private const val FORMAT_TAG = 22
        private const val FOREGROUND_NOTIFICATION_ID = 123
        private const val NOTIFICATION_CHANNEL_NORMAL = "NOTIFICATION_CHANNEL_NORMAL"
        private const val ACTIVITY_CODE = 234
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setUpNotificationChannel()
        }
    }

    private fun setUpNotificationChannel() {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_NORMAL,
                "Normal notification", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        makeServiceForeground()
        runTask()

        return Service.START_NOT_STICKY
    }

    private fun makeServiceForeground() {
        val builder = NotificationCompat.Builder(
            this,
            NOTIFICATION_CHANNEL_NORMAL
        )

        val activityIntent = Intent(this, MainActivity::class.java)
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, ACTIVITY_CODE, activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        builder.setContentTitle("Foreground service notification content title")
            .setContentInfo("Service is running in the foreground.")
            .setContentIntent(pendingIntent)
            .setContentText("This is content text")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setOngoing(true)

        // Starts foreground
        startForeground(FOREGROUND_NOTIFICATION_ID, builder.build())
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("StaticFieldLeak")
    private fun runTask() {

        object : YouTubeExtractor(this) {
            public override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
                if (ytFiles != null) {

                    val url = ytFiles[FORMAT_TAG].url
                    Log.d("zavanton", url)

                    downloadFile(url, "/path/filename")
                }
            }
        }.extract(LINK, true, true)
    }

    private fun downloadFile(url: String, filename: String) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url)
            .build()
        val response = client.newCall(request).execute()

        val inputStream = response.body()?.byteStream()

        inputStream?.toFile(filename)

        response.body()?.close()
    }

    private fun InputStream.toFile(path: String) {
        File(path).outputStream().use { this.copyTo(it) }
    }
}
