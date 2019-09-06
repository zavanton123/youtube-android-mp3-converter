package com.zavanton.yoump3.core.business

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.zavanton.yoump3.core.utils.Log
import io.reactivex.ObservableEmitter
import javax.inject.Inject

class ConversionManager
@Inject
constructor(private val ffmpeg: FFmpeg) {

    companion object {
        private const val TARGET = "time="
        private const val TARGET_OFFSET = 8
    }

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
                    Log.d(message)
                    val progress = fetchProgress(message)
                    emitter.onNext(progress)
                }

                override fun onFailure(message: String) {
                    val throwable = Throwable(message)
                    Log.e(throwable, message)
                    emitter.onError(throwable)
                }

                override fun onSuccess(message: String) {
                    Log.d(message)
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

    private fun fetchProgress(message: String): String {
        Log.d()

        // TODO
        return if (message.contains(TARGET)) {
            message

//            val regex = "^[0-2][0-3]:[0-5][0-9]:[0-5][0-9]\$"
//            val pattern = Pattern.compile(regex)
//            val matcher = pattern.matcher(message)
//            matcher.group(1)
        } else {
            ""
        }
    }
}