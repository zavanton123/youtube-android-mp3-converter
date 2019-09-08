package com.zavanton.yoump3.download.business.interactor

import com.zavanton.yoump3.core.di.ServiceScope
import com.zavanton.yoump3.download.business.model.Event
import com.zavanton.yoump3.download.data.IDownloadRepository
import io.reactivex.Observable
import javax.inject.Inject

@ServiceScope
class DownloadInteractor @Inject constructor(
    private val downloadRepository: IDownloadRepository
) : IDownloadInteractor {

    override fun downloadFile(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String
    ): Observable<Event> = downloadRepository.download(
        urlLink,
        downloadsFolder,
        targetFilename,
        videoExtension
    )
}