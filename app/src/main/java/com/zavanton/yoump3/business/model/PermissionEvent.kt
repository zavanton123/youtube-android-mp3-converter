package com.zavanton.yoump3.business.model

sealed class PermissionEvent {

    object PermissionGranted : PermissionEvent()
    object RepeatPermissionRequest : PermissionEvent()
}