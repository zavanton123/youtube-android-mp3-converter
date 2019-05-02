package com.zavanton.yoump3.domain.interactor

import io.reactivex.Single

interface IConvertInteractor {

    fun convertToMp3(): Single<Boolean>
}