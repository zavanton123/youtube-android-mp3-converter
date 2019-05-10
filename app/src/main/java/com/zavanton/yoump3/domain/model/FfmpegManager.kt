package com.zavanton.yoump3.domain.model

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.zavanton.yoump3.utils.Logger
import io.reactivex.ObservableEmitter
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

    fun convert(emitter: ObservableEmitter<String>, commands: Array<String>) {
        try {
            ffmpeg.execute(commands, object : ExecuteBinaryResponseHandler() {

                override fun onStart() {
                    Logger.d("onStart")
                }

                override fun onProgress(message: String) {
                    Logger.d("onProgress: $message")
                    emitter.onNext(message)
                }

                override fun onFailure(message: String) {
                    val throwable = Throwable(message)
                    Logger.e("onFailure: $message", throwable)
                    emitter.onError(throwable)
                }

                override fun onSuccess(message: String) {
                    Logger.d("onSuccess: $message")
                    emitter.onNext(message)
                }

                override fun onFinish() {
                    Logger.d("onFinish")
                    emitter.onComplete()
                }
            })
        } catch (exception: FFmpegCommandAlreadyRunningException) {
            Logger.e("FfmpegManager - FFmpegCommandAlreadyRunningException", exception)
            emitter.onError(exception)
        }
    }
}