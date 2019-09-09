package com.zavanton.yoump3.download.eventBus

import com.zavanton.yoump3.download.business.model.Event
import io.reactivex.subjects.BehaviorSubject

class EventBus {

    private val channel = BehaviorSubject.create<Event>()

    fun listen(): BehaviorSubject<Event> = channel

    fun send(event: Event) {
        channel.onNext(event)
    }
}