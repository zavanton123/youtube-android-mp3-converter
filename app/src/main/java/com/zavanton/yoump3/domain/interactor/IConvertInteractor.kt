package com.zavanton.yoump3.domain.interactor

import io.reactivex.Observable

interface IConvertInteractor {

    fun convertToMp3(videoFile: String, audioFile: String): Observable<String>
}