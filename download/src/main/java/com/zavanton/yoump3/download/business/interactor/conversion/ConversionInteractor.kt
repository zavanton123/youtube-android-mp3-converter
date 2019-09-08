package com.zavanton.yoump3.download.business.interactor.conversion

import com.zavanton.yoump3.core.di.IoThreadScheduler
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.download.data.ConversionService
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class ConversionInteractor
@Inject
constructor(
    private val conversionService: ConversionService,
    @IoThreadScheduler
    private val ioThreadScheduler: Scheduler
) : IConversionInteractor {

    override fun convertToMp3(videoFile: String, audioFile: String): Observable<String> {
        Log.d()
        return Observable.create {
            ioThreadScheduler.scheduleDirect {
                conversionService.convert(it, arrayOf("-i", videoFile, audioFile))
            }
        }
    }
}