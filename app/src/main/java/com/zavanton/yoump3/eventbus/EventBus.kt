package com.zavanton.yoump3.eventbus

import com.zavanton.yoump3.utils.Log
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventBus
@Inject
constructor() {

    init {
        Log.d()
    }

    private val channel = BehaviorSubject.create<Message>()

    fun listenForMessages(): BehaviorSubject<Message> {
        Log.d()
        return channel
    }

    fun send(message: Message) {
        Log.d("message: $message")
        channel.onNext(message)
    }
}