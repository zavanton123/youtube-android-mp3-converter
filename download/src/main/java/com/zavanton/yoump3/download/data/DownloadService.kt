package com.zavanton.yoump3.download.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseArray
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.zavanton.yoump3.core.di.ApplicationContext
import com.zavanton.yoump3.core.di.IoThreadScheduler
import com.zavanton.yoump3.core.di.ServiceScope
import com.zavanton.yoump3.core.utils.Constants.EMPTY_STRING
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.download.business.model.YoutubeTags
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

@SuppressLint("StaticFieldLeak")
@ServiceScope
class DownloadService @Inject constructor(
    @ApplicationContext
    private val appContext: Context,
    private val client: OkHttpClient,
    @IoThreadScheduler
    private val ioThreadScheduler: Scheduler
) : IDownloadService, YouTubeExtractor(appContext) {

    private var downloadsFolder: String = EMPTY_STRING
    private var targetFilename: String = EMPTY_STRING
    private var videoExtension: String = EMPTY_STRING
    private var emitter: ObservableEmitter<Int>? = null

    override fun download(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String
    ): Observable<Int> =
        Observable.create<Int> { emitter ->
            this.downloadsFolder = downloadsFolder
            this.targetFilename = targetFilename
            this.videoExtension = videoExtension
            this.emitter = emitter
            this.extract(urlLink, true, true)
        }

    override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
        if (ytFiles != null) {
            val url = getUrl(ytFiles)
            downloadFileFromUrl(url, downloadsFolder, targetFilename, videoExtension, emitter)
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
        emitter: ObservableEmitter<Int>?
    ) {
        ioThreadScheduler.scheduleDirect {
            url?.apply {
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                val inputStream = response.body()?.byteStream()
                val outputFile = File("$downloadsFolder/$targetFilename.$videoExtension")

                inputStream?.apply {
                    writeToFile(this, outputFile, emitter)
                }
                response.body()?.close()
            }
        }
    }

    // TODO get the file size and send download progress relative to the total file size
    private fun writeToFile(
        inputStream: InputStream,
        file: File,
        emitter: ObservableEmitter<Int>?
    ) {
        Log.d()
        try {
            val fileOutputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length = inputStream.read(buffer)
            var downloaded = 0
            while (length > 0) {
                fileOutputStream.write(buffer, 0, length)
                length = inputStream.read(buffer)
                downloaded += length
                emitter?.onNext(downloaded)
            }
            fileOutputStream.close()
            inputStream.close()

            emitter?.onComplete()

        } catch (exception: IOException) {
            Log.e(exception, "Error while writing to output file")
            emitter?.onError(exception)
        }
    }
}