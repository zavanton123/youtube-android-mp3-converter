package com.zavanton.yoump3.ui.download.view

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.zavanton.yoump3.R
import com.zavanton.yoump3.di.qualifier.channel.NormalNotificationChannel
import com.zavanton.yoump3.ui.download.di.manager.DownloadServiceComponentManager
import com.zavanton.yoump3.ui.download.presenter.IDownloadServicePresenter
import com.zavanton.yoump3.ui.main.activity.view.MainActivity
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

class DownloadService : Service(), IDownloadService {

    companion object {

        private const val FOREGROUND_NOTIFICATION_ID = 123
        private const val ACTIVITY_CODE = 234
    }

    @Inject
    lateinit var presenterDownloadService: IDownloadServicePresenter

    @Inject
    @field:NormalNotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()
        Logger.d("DownloadService - onCreate")

        DownloadServiceComponentManager.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.d("DownloadService - onStartCommand")

        presenterDownloadService.bind(this)
        presenterDownloadService.onStartCommand()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        Logger.d("DownloadService - onDestroy")
        super.onDestroy()

        presenterDownloadService.unbind(this)
        DownloadServiceComponentManager.clear()
    }

    override fun startForeground() {
        Logger.d("DownloadService - startForeground")

        val activityIntent = Intent(this, MainActivity::class.java)
            .apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

        val pendingIntent = PendingIntent.getActivity(
            this, ACTIVITY_CODE, activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        notificationBuilder
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setContentTitle(getString(R.string.notification_title))
            .setContentInfo(getString(R.string.notification_content_info))
            .setContentText(getString(R.string.notification_content_text))
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
            .apply {
                startForeground(FOREGROUND_NOTIFICATION_ID, this)
            }
    }

    override fun stopForeground() {
        stopForeground(true)
    }
}