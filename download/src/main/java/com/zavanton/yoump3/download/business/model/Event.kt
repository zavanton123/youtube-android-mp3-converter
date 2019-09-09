package com.zavanton.yoump3.download.business.model

sealed class Event {

    data class SendActionUrl(val url: String) : Event()
    data class SendDownloadUrl(val url: String) : Event()

    object DownloadStarted : Event()
    data class DownloadProgress(val progress: String) : Event()
    object DownloadSuccess : Event()
    object DownloadError : Event()

    object ConversionStarted : Event()
    data class ConversionProgress(val progress: String) : Event()
    object ConversionSuccess : Event()
    object ConversionError : Event()
}