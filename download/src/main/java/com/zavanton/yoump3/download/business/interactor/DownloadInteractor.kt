package com.zavanton.yoump3.download.business.interactor

import com.zavanton.yoump3.core.di.ServiceScope
import com.zavanton.yoump3.download.business.model.Event
import com.zavanton.yoump3.download.data.IConversionService
import com.zavanton.yoump3.download.data.IDownloadRepository
import io.reactivex.Observable
import javax.inject.Inject

@ServiceScope
class DownloadInteractor @Inject constructor(
    private val downloadRepository: IDownloadRepository,
    private val conversionService: IConversionService
) : IDownloadInteractor {

    companion object {

        private const val PREFIX = "-i"
    }

    override fun downloadFile(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String,
        videoFile: String,
        audioFile: String
    ): Observable<Event> =
        downloadRepository.download(
            urlLink,
            downloadsFolder,
            targetFilename,
            videoExtension
        ).concatMap {
            conversionService.convert(arrayOf(PREFIX, videoFile, audioFile))
        }
}