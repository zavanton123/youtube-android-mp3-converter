package com.zavanton.yoump3.core.eventBus

data class Message(

    val event: Event,

    val text: String? = null
)