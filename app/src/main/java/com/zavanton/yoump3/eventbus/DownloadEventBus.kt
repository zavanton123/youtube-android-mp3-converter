package com.zavanton.yoump3.eventbus

import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadEventBus
@Inject
constructor() {

    private val downloadChannel = BehaviorSubject.create<String>()

    fun listenForMessages(): BehaviorSubject<String> {
        return downloadChannel
    }

    fun sendMessage(message: String) {
        downloadChannel.onNext(message)
    }
}