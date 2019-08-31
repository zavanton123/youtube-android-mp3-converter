package com.zavanton.yoump3.eventbus

enum class Event {

    CLIPBOARD_URL,
    INTENT_ACTION_URL,
    DOWNLOAD_URL,

    CLIPBOARD_EMPTY,
    CLIPBOARD_NOT_EMPTY,

    URL_INVALID,
    URL_VALID,

    DOWNLOAD_STARTED,
    DOWNLOAD_PROGRESS,
    DOWNLOAD_SUCCESS,
    DOWNLOAD_ERROR,

    CONVERSION_STARTED,
    CONVERSION_PROGRESS,
    CONVERSION_SUCCESS,
    CONVERSION_ERROR,

}