package com.zavanton.yoump3.eventbus

import com.zavanton.yoump3.core.di.AppScope
import com.zavanton.yoump3.core.utils.Log
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@AppScope
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