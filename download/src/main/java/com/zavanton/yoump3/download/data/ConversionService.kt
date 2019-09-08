package com.zavanton.yoump3.download.data

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.zavanton.yoump3.core.di.ServiceScope
import com.zavanton.yoump3.core.utils.Constants
import com.zavanton.yoump3.core.utils.Log
import io.reactivex.Observable
import javax.inject.Inject

@ServiceScope
class ConversionService @Inject constructor(
    private val ffmpeg: FFmpeg
) : IConversionService {

    companion object {

        private const val TARGET = "time="
    }

    override fun convert(commands: Array<String>): Observable<String> =
        Observable.create<String> { emitter ->
            try {
                ffmpeg.execute(commands, object : ExecuteBinaryResponseHandler() {

                    override fun onStart() {
                        Log.d()
                    }

                    override fun onProgress(message: String) {
                        emitter.onNext(fetchProgress(message))
                    }

                    override fun onFailure(message: String) {
                        emitter.onError(Throwable(message))
                    }

                    override fun onSuccess(message: String) {
                        emitter.onNext(message)
                    }

                    override fun onFinish() {
                        emitter.onComplete()
                    }
                })
            } catch (exception: FFmpegCommandAlreadyRunningException) {
                emitter.onError(exception)
            }
        }

    private fun fetchProgress(message: String): String =
        if (message.contains(TARGET)) {
            message
        } else {
            Constants.EMPTY_STRING
        }
}