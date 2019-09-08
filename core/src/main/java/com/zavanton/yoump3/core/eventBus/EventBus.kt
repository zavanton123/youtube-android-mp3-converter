package com.zavanton.yoump3.core.eventBus

import com.zavanton.yoump3.core.di.AppScope
import com.zavanton.yoump3.core.utils.Log
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@AppScope
class EventBus @Inject constructor() {

    init {
        Log.i()
    }

    private val channel = BehaviorSubject.create<Event>()

    fun listen(): BehaviorSubject<Event> = channel

    fun send(event: Event) {
        channel.onNext(event)
    }
}