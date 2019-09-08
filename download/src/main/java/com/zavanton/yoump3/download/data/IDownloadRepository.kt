package com.zavanton.yoump3.download.data

import com.zavanton.yoump3.download.business.model.Event
import io.reactivex.Observable

interface IDownloadRepository {

    fun download(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String
    ): Observable<Event>
}