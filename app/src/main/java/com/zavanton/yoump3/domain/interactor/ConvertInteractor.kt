package com.zavanton.yoump3.domain.interactor

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.zavanton.yoump3.ui.download.presenter.DownloadPresenter
import com.zavanton.yoump3.utils.Logger
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class ConvertInteractor
@Inject
constructor(private val ffmpeg: FFmpeg) : IConvertInteractor {

    override fun convertToMp3(): Single<Boolean> {
        return Single.fromCallable { convert() }
    }

    private fun convert(): Boolean {

        try {
            val videoFile =
                "${DownloadPresenter.DOWNLOADS_FOLDER}/${DownloadPresenter.TARGET_FILENAME}.${DownloadPresenter.VIDEO_EXTENSION}"

            val audioFile =
                "${DownloadPresenter.DOWNLOADS_FOLDER}/${DownloadPresenter.TARGET_FILENAME}.${DownloadPresenter.AUDIO_EXTENSION}"

            val commands = arrayOf("-i", videoFile, audioFile)

            ffmpeg.execute(commands, object : ExecuteBinaryResponseHandler() {

                override fun onStart() {
                    Logger.d("onStart")
                }

                override fun onProgress(message: String?) {
                    Logger.d("onProgress: $message")
                }

                override fun onFailure(message: String?) {
                    Logger.d("onFailure: $message")
                }

                override fun onSuccess(message: String?) {
                    Logger.d("onSuccess: $message")
                }

                override fun onFinish() {
                    Logger.d("onFinish")
                    File("${DownloadPresenter.DOWNLOADS_FOLDER}/${DownloadPresenter.TARGET_FILENAME}.${DownloadPresenter.VIDEO_EXTENSION}").delete()
                    service?.stopForeground()
                }
            })
        } catch (e: FFmpegCommandAlreadyRunningException) {
            Logger.e("FFmpegCommandAlreadyRunningException", e)
        } finally {
            return true
        }
    }
}