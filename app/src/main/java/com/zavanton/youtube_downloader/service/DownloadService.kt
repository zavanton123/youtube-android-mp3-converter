package com.zavanton.youtube_downloader.service

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.util.SparseArray
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile

class DownloadService : IntentService("DownloadService") {

    companion object {

        private val LINK = "https://www.youtube.com/watch?v=IGQBtbKSVhY"
        private val FORMAT_TAG = 22
        private val FOREGROUND_NOTIFICATION_ID = 123
        private val NOTIFICATION_CHANNEL_HIGH = "NOTIFICATION_CHANNEL_HIGH"
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d("zavanton", "onHandleIntent")
        makeServiceForeground()
        runTask()
    }

    private fun makeServiceForeground() {
        val builder = NotificationCompat.Builder(
            this,
            NOTIFICATION_CHANNEL_HIGH
        )

        builder.setContentTitle("Foreground service title")
            .setContentText("Foreground service text")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setOngoing(true)

        startForeground(FOREGROUND_NOTIFICATION_ID, builder.build())
    }

    @SuppressLint("StaticFieldLeak")
    private fun runTask() {

        object : YouTubeExtractor(this) {
            public override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
                Log.d("zavanton", "onExtractionComplete")
                if (ytFiles != null) {

                    val url = ytFiles[FORMAT_TAG].url
                    Log.d("zavanton", url)
                }
            }
        }.extract(LINK, true, true)
    }
}
