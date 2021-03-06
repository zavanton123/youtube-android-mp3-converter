package com.zavanton.yoump3.download.ui.view

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.zavanton.yoump3.core.di.NormalNotificationChannel
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.download.R
import com.zavanton.yoump3.download.di.DownloadServiceComponentManager
import com.zavanton.yoump3.download.ui.presenter.IDownloadServicePresenter
import javax.inject.Inject

class DownloadService : Service(), IDownloadService {

    companion object {

        private const val FOREGROUND_NOTIFICATION_ID = 123
        private const val ACTIVITY_CODE = 234
    }

    @Inject
    lateinit var presenterDownloadService: IDownloadServicePresenter

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    @field:NormalNotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder

    init {
        Log.d()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d()

        DownloadServiceComponentManager.getDownloadServiceComponent()
            .inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d()

        presenterDownloadService.bind(this)
        presenterDownloadService.onStartCommand()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        Log.d()
        super.onDestroy()

        presenterDownloadService.unbind(this)
        DownloadServiceComponentManager.clear()
    }

    override fun startForeground() {
        Log.d()

        val activityIntent = Intent()
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
        Log.d()
        notificationManager.cancel(FOREGROUND_NOTIFICATION_ID)
        stopForeground(true)
    }
}