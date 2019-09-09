package com.zavanton.yoump3.download.business.interactor

import com.zavanton.yoump3.core.di.ServiceScope
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.download.business.model.Event
import com.zavanton.yoump3.download.data.conversion.IConversionService
import com.zavanton.yoump3.download.data.download.IDownloadRepositoryFactory
import io.reactivex.Observable
import javax.inject.Inject

@ServiceScope
class DownloadInteractor @Inject constructor(
    private val downloadRepositoryFactory: IDownloadRepositoryFactory,
    private val conversionService: IConversionService
) : IDownloadInteractor {

    companion object {

        private const val PREFIX = "-i"
    }

    init {
        Log.d()
    }

    override fun downloadFile(
        urlLink: String,
        downloadsFolder: String,
        targetFilename: String,
        videoExtension: String,
        videoFile: String,
        audioFile: String
    ): Observable<Event> =
        downloadRepositoryFactory.newInstance()
            .download(
                urlLink,
                downloadsFolder,
                targetFilename,
                videoExtension
            ).flatMap {
                if (it is Event.DownloadSuccess) {
                    conversionService.convert(arrayOf(PREFIX, videoFile, audioFile))
                } else {
                    Observable.just(it)
                }
            }
}