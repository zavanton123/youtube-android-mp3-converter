package com.zavanton.yoump3.download.data

import io.reactivex.Observable

interface IConversionService {

    fun convert(commands: Array<String>): Observable<String>
}