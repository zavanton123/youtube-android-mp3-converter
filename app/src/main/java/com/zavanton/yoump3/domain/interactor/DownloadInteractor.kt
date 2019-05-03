package com.zavanton.yoump3.domain.interactor

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseArray
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.zavanton.yoump3.di.qualifier.context.ApplicationContext
import com.zavanton.yoump3.utils.Logger
import com.zavanton.yoump3.utils.YoutubeTags
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.InputStream
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DownloadInteractor
@Inject
constructor(
    @ApplicationContext
    private val appContext: Context
) : IDownloadInteractor {

    @SuppressLint("StaticFieldLeak")
    override fun downloadFile(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String
    ): Observable<Boolean> {

        val executor = Executors.newSingleThreadScheduledExecutor()

        return Observable.create { emitter ->

            object : YouTubeExtractor(appContext) {

                override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
                    Logger.d("onExtractionComplete")
                    if (ytFiles != null) {

                        val url = getUrl(ytFiles)

                        url?.apply {

                            val future = executor.schedule({
                                val isDownloaded = download(url, "$downloadsFolder/$targetFilename.$videoExtension")
                                Logger.d("isDownloaded: $isDownloaded")
                                emitter.onNext(isDownloaded)
                            }, 10, TimeUnit.SECONDS)

                            emitter.setCancellable {
                                future.cancel(false)
                            }
                        }
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

    private fun download(url: String, filename: String): Boolean {
        try {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val inputStream = response.body()?.byteStream()
            inputStream?.toFile(filename)
            response.body()?.close()
            Logger.d("file downloaded!")

            return true

        } catch (e: Exception) {
            Logger.e("Some error while downloading file", e)

            return false
        }
    }

    private fun InputStream.toFile(path: String) {
        File(path).outputStream()
            .use { this.copyTo(it) }
    }
}