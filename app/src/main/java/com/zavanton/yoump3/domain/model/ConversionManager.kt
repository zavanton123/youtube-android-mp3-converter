package com.zavanton.yoump3.domain.model

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.zavanton.yoump3.utils.Log
import io.reactivex.ObservableEmitter
import javax.inject.Inject

class ConversionManager
@Inject
constructor(private val ffmpeg: FFmpeg) {

    fun init() {
        Log.d()
        try {
            ffmpeg.loadBinary(object : LoadBinaryResponseHandler() {

                override fun onStart() {
                    Log.d()
                }

                override fun onFailure() {
                    Log.d()
                }

                override fun onSuccess() {
                    Log.d()
                }

                override fun onFinish() {
                    Log.d()
                }
            })
        } catch (exception: FFmpegNotSupportedException) {
            Log.e(exception)
        }
    }

    fun convert(emitter: ObservableEmitter<String>, commands: Array<String>) {
        try {
            ffmpeg.execute(commands, object : ExecuteBinaryResponseHandler() {

                override fun onStart() {
                    Log.d()
                }

                override fun onProgress(message: String) {
                    Log.d("$message")
                    // TODO show percent of conversion completed
                    emitter.onNext(message)
                }

                override fun onFailure(message: String) {
                    val throwable = Throwable(message)
                    Log.e(throwable, message)
                    emitter.onError(throwable)
                }

                override fun onSuccess(message: String) {
                    Log.d("$message")
                    emitter.onNext(message)
                }

                override fun onFinish() {
                    Log.d()
                    emitter.onComplete()
                }
            })
        } catch (exception: FFmpegCommandAlreadyRunningException) {
            Log.e(exception)
            emitter.onError(exception)
        }
    }
}