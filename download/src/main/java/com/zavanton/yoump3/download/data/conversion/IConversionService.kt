package com.zavanton.yoump3.download.data.conversion

import com.zavanton.yoump3.download.business.model.Event
import io.reactivex.Observable

interface IConversionService {

    fun convert(commands: Array<String>): Observable<Event>
}