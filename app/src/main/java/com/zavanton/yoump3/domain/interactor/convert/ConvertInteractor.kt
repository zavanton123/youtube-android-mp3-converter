package com.zavanton.yoump3.domain.interactor.convert

import com.zavanton.yoump3.di.qualifier.scheduler.IoThreadScheduler
import com.zavanton.yoump3.domain.model.FfmpegManager
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class ConvertInteractor
@Inject
constructor(
    private val ffmpegManager: FfmpegManager,
    @IoThreadScheduler
    private val ioThreadScheduler: Scheduler
) : IConvertInteractor {

    override fun convertToMp3(videoFile: String, audioFile: String): Observable<String> =
        Observable.create {
            ioThreadScheduler.scheduleDirect {
                ffmpegManager.convert(it, arrayOf("-i", videoFile, audioFile))
            }
        }
}