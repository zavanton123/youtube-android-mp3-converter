package com.zavanton.yoump3.domain.interactor

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseArray
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.zavanton.yoump3.di.qualifier.context.ApplicationContext
import com.zavanton.yoump3.di.qualifier.scheduler.IoThreadScheduler
import com.zavanton.yoump3.utils.Logger
import com.zavanton.yoump3.utils.YoutubeTags
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class DownloadInteractor
@Inject
constructor(
    @ApplicationContext
    private val context: Context,
    @IoThreadScheduler
    private val ioThreadScheduler: Scheduler
) : IDownloadInteractor {

    @SuppressLint("StaticFieldLeak")
    override fun downloadFile(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String
    ): Observable<String> {

        return Observable.create { emitter ->

            object : YouTubeExtractor(context) {

                override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
                    Logger.d("onExtractionComplete")
                    if (ytFiles != null) {

                        val url = getUrl(ytFiles)

                        downloadFileFromUrl(url, downloadsFolder, targetFilename, videoExtension, emitter)
                    }
                }
            }.extract(urlLink, true, true)
        }
    }

    private fun getUrl(ytFiles: SparseArray<YtFile>): String? {
        var youtubeFile: YtFile? = null
        for (tag in YoutubeTags.ALL) {

            if (ytFiles[tag] != null) {
                youtubeFile = ytFiles[tag]
            }
        }

        return youtubeFile?.url
    }

    private fun downloadFileFromUrl(
        url: String?,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String,
        emitter: ObservableEmitter<String>
    ) {
        url?.apply {

            ioThreadScheduler.scheduleDirect {

                val client = OkHttpClient()

                val request = Request.Builder().url(url).build()

                val response = client.newCall(request).execute()

                val inputStream = response.body()?.byteStream()

                val outputFile = File("$downloadsFolder/$targetFilename.$videoExtension")

                inputStream?.apply {
                    writeInputStreamToFile(this, outputFile, emitter)
                }

                response.body()?.close()
            }
        }
    }

    private fun writeInputStreamToFile(
        inputStream: InputStream,
        file: File,
        emitter: ObservableEmitter<String>
    ) {
        try {
            val out = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len = inputStream.read(buf)
            while (len > 0) {
                out.write(buf, 0, len)
                len = inputStream.read(buf)
            }
            out.close()
            inputStream.close()

            Logger.d("File is downloaded!")
            emitter.onNext("File is downloaded")

        } catch (exception: IOException) {
            Logger.e("Error while writing to output file", exception)
            emitter.onError(exception)
        }
    }
}