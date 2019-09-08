package com.zavanton.yoump3.download.business.interactor

import com.zavanton.yoump3.core.di.ServiceScope
import com.zavanton.yoump3.download.business.model.Event
import com.zavanton.yoump3.download.data.ConversionService
import io.reactivex.Observable
import javax.inject.Inject

@ServiceScope
class ConversionInteractor @Inject constructor(
    private val conversionService: ConversionService
) : IConversionInteractor {

    companion object {

        private const val PREFIX = "-i"
    }

    override fun convertToMp3(videoFile: String, audioFile: String): Observable<Event> =
        conversionService.convert(arrayOf(PREFIX, videoFile, audioFile))
}