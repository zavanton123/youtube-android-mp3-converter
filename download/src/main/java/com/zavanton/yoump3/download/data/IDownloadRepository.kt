package com.zavanton.yoump3.download.data

import io.reactivex.Observable

interface IDownloadRepository {

    fun download(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String
    ): Observable<Int>
}