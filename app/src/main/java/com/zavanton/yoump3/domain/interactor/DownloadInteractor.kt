package com.zavanton.yoump3.domain.interactor

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class DownloadInteractor
@Inject
constructor() : IDownloadInteractor {

    override fun downloadFile(
        url: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String
    ): Single<Boolean> =
        Single.fromCallable {
            val filename = "$downloadsFolder/$targetFilename.$videoExtension"
            download(url, filename)
        }

    private fun download(url: String, filename: String): Boolean {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()

        val inputStream = response.body()?.byteStream()

        inputStream?.toFile(filename)
        response.body()?.close()

        return true
    }

    private fun InputStream.toFile(path: String) {
        File(path).outputStream()
            .use { this.copyTo(it) }
    }
}