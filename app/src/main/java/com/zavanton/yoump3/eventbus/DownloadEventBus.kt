package com.zavanton.yoump3.eventbus

import com.zavanton.yoump3.utils.Logger
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadEventBus
@Inject
constructor() {

    init {
        Logger.d("DownloadEventBus is init")
    }

    private val downloadChannel = BehaviorSubject.create<String>()

    fun listenForMessages(): BehaviorSubject<String> {
        Logger.d("DownloadEventBus - listenForMessages")
        return downloadChannel
    }

    fun sendMessage(message: String) {
        Logger.d("DownloadEventBus - sendMessage: $message")
        downloadChannel.onNext(message)
    }
}