package com.zavanton.yoump3.eventbus

import com.zavanton.yoump3.utils.Log
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventBus @Inject constructor() {

    init {
        Log.i()
    }

    private val channel = BehaviorSubject.create<Message>()

    fun listenForMessages(): BehaviorSubject<Message> = channel

    fun send(message: Message) {
        channel.onNext(message)
    }
}