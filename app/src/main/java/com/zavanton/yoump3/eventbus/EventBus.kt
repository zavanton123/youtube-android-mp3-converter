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
        Log.i()
    }

    private val channel = BehaviorSubject.create<Message>()

    fun listenForMessages(): BehaviorSubject<Message> {
        Log.i()
        return channel
    }

    fun send(message: Message) {
        Log.i("message: $message")
        channel.onNext(message)
    }
}