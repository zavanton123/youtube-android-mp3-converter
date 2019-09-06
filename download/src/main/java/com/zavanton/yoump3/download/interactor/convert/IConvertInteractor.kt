package com.zavanton.yoump3.download.interactor.convert

import io.reactivex.Observable

interface IConvertInteractor {

    fun convertToMp3(videoFile: String, audioFile: String): Observable<String>
}