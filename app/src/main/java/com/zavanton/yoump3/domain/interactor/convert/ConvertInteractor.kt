package com.zavanton.yoump3.domain.interactor.convert

import com.zavanton.yoump3.core.di.IoThreadScheduler
import com.zavanton.yoump3.core.business.ConversionManager
import com.zavanton.yoump3.core.utils.Log
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class ConvertInteractor
@Inject
constructor(
    private val conversionManager: ConversionManager,
    @IoThreadScheduler
    private val ioThreadScheduler: Scheduler
) : IConvertInteractor {

    override fun convertToMp3(videoFile: String, audioFile: String): Observable<String> {
        Log.d()
        return Observable.create {
            ioThreadScheduler.scheduleDirect {
                conversionManager.convert(it, arrayOf("-i", videoFile, audioFile))
            }
        }
    }
}