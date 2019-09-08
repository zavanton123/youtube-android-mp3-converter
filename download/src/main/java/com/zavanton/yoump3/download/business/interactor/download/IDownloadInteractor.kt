package com.zavanton.yoump3.download.business.interactor.download

import io.reactivex.Observable

interface IDownloadInteractor {

    fun downloadFile(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String
    ): Observable<Int>
}
