package com.zavanton.youtube_downloader.app

import android.app.Application
import android.util.Log
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException

class TheApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initFfmpeg()
    }

    private fun initFfmpeg() {
        val ffmpeg = FFmpeg.getInstance(this)
        try {
            ffmpeg.loadBinary(object : LoadBinaryResponseHandler() {

                override fun onStart() {}

                override fun onFailure() {}

                override fun onSuccess() {
                    Log.d("zavantondebug", "Succeeded to load FFMPEG")
                }

                override fun onFinish() {}
            })
        } catch (e: FFmpegNotSupportedException) {
            Log.d("zavantondebug", "Failed to load FFMPEG")
        }
    }
}