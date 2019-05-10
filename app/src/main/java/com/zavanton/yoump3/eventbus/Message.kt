package com.zavanton.yoump3.eventbus

data class Message(

    val event: Event,

    val text: String? = null
)