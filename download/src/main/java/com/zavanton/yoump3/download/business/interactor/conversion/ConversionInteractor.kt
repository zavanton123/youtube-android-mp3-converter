package com.zavanton.yoump3.download.business.interactor.conversion

import com.zavanton.yoump3.core.di.ServiceScope
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.download.data.ConversionService
import io.reactivex.Observable
import javax.inject.Inject

@ServiceScope
class ConversionInteractor @Inject constructor(
    private val conversionService: ConversionService
) : IConversionInteractor {

    override fun convertToMp3(videoFile: String, audioFile: String): Observable<String> {
        Log.d()
        return conversionService.convert(arrayOf("-i", videoFile, audioFile))
    }
}