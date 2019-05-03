package com.zavanton.yoump3.domain.interactor

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.zavanton.yoump3.di.qualifier.scheduler.IoThreadScheduler
import com.zavanton.yoump3.utils.Logger
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class ConvertInteractor
@Inject
constructor(
    private val ffmpeg: FFmpeg,
    @IoThreadScheduler
    private val ioThreadScheduler: Scheduler
) : IConvertInteractor {

    override fun convertToMp3(videoFile: String, audioFile: String): Observable<String> =
        Observable.create { emitter ->

            ioThreadScheduler.scheduleDirect {

                val commands = arrayOf("-i", videoFile, audioFile)

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
                    Logger.e("FFmpegCommandAlreadyRunningException", exception)
                    emitter.onError(exception)
                }
            }
        }
}