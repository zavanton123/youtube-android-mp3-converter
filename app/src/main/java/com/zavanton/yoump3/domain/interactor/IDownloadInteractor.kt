package com.zavanton.yoump3.domain.interactor

import io.reactivex.Single

interface IDownloadInteractor {

    fun downloadFile(
        url: String,
        downloadsFolder: String,
        filename: String,
        videoExtension: String
    ): Single<Boolean>
}
