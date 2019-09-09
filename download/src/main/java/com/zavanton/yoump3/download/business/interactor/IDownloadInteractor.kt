package com.zavanton.yoump3.download.business.interactor

import com.zavanton.yoump3.download.business.model.Event
import io.reactivex.Observable

interface IDownloadInteractor {

    fun downloadFile(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String,
        videoFile: String,
        audioFile: String
    ): Observable<Event>
}