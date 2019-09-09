package com.zavanton.yoump3.business.interactor

import com.zavanton.yoump3.business.model.PermissionEvent
import io.reactivex.Observable

interface IPermissionInteractor {

    fun checkPermissions(): Observable<PermissionEvent>
}