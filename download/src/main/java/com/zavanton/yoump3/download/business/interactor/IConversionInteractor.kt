package com.zavanton.yoump3.download.business.interactor

import io.reactivex.Observable

interface IConversionInteractor {

    fun convertToMp3(videoFile: String, audioFile: String): Observable<String>
}