package com.zavanton.yoump3.domain.interactor

import android.os.Environment
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.zavanton.yoump3.utils.Logger
import io.reactivex.Single
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ConvertInteractor
@Inject
constructor(private val ffmpeg: FFmpeg) : IConvertInteractor {

    companion object {

        private val TARGET_FILENAME = "Youtube-" + SimpleDateFormat("yyyy.MM.dd-HH-mm-ss", Locale.US).format(Date())
        private const val VIDEO_EXTENSION = "mp4"
        private const val AUDIO_EXTENSION = "mp3"
        private val DOWNLOADS_FOLDER =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
    }

    override fun convertToMp3(): Single<Boolean> {
        return Single.fromCallable { convert() }
    }

    private fun convert(): Boolean {

        try {
            val videoFile =
                "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION"

            val audioFile =
                "$DOWNLOADS_FOLDER/$TARGET_FILENAME.$AUDIO_EXTENSION"

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
                    File("$DOWNLOADS_FOLDER/$TARGET_FILENAME.$VIDEO_EXTENSION").delete()
                }
            })
        } catch (e: FFmpegCommandAlreadyRunningException) {
            Logger.e("FFmpegCommandAlreadyRunningException", e)
        } finally {
            return true
        }
    }
}