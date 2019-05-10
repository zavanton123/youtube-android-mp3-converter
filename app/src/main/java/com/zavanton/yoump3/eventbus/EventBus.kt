package com.zavanton.yoump3.eventbus

import com.zavanton.yoump3.utils.Logger
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventBus
@Inject
constructor() {

    init {
        Logger.d("EventBus is init")
    }

    private val channel = BehaviorSubject.create<Message>()

    fun listenForMessages(): BehaviorSubject<Message> {
        Logger.d("EventBus - startListeningForMessages")
        return channel
    }

    fun send(message: Message) {
        Logger.d("EventBus - send: type - ${message.event} text - ${message.text}")
        channel.onNext(message)
    }
}