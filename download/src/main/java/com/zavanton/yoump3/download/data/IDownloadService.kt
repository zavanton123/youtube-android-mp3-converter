package com.zavanton.yoump3.download.data

import io.reactivex.Observable

interface IDownloadService {

    fun download(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String
    ): Observable<Int>
}