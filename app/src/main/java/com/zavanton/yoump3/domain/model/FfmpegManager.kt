package com.zavanton.yoump3.domain.model

import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

class FfmpegManager
@Inject
constructor(private val ffmpeg: FFmpeg) {

    fun init() {
        try {
            ffmpeg.loadBinary(object : LoadBinaryResponseHandler() {

                override fun onStart() {
                    Logger.d("FFmpeg - onStart")
                }

                override fun onFailure() {
                    Logger.d("FFmpeg - onFailure")
                }

                override fun onSuccess() {
                    Logger.d("FFmpeg - onSuccess")
                }

                override fun onFinish() {
                    Logger.d("FFmpeg - onFinish")
                }
            })
        } catch (exception: FFmpegNotSupportedException) {
            Logger.e("FFmpeg - Error while initializing FFmpeg", exception)
        }
    }
}