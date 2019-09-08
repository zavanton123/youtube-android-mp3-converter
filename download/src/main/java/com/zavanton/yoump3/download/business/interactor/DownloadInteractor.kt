package com.zavanton.yoump3.download.business.interactor

import com.zavanton.yoump3.core.di.ServiceScope
import com.zavanton.yoump3.download.data.IDownloadService
import io.reactivex.Observable
import javax.inject.Inject

@ServiceScope
class DownloadInteractor @Inject constructor(
    private val downloadService: IDownloadService
) : IDownloadInteractor {

    override fun downloadFile(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String
    ): Observable<Int> = downloadService.download(
        urlLink,
        downloadsFolder,
        targetFilename,
        videoExtension
    )
}