package com.zavanton.yoump3.data

import com.zavanton.yoump3.business.model.PermissionEvent
import io.reactivex.Observable

interface IPermissionService {

    fun checkPermissions(permissions: Array<String>): Observable<PermissionEvent>
}