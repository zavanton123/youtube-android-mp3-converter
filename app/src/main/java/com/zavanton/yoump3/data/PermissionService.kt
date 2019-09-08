package com.zavanton.yoump3.data

import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.core.di.ActivityScope
import com.zavanton.yoump3.business.model.PermissionEvent
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class PermissionService @Inject constructor(
    private val rxPermissions: RxPermissions
) : IPermissionService {

    override fun checkPermissions(permissions: Array<String>): Observable<PermissionEvent> =
        rxPermissions.request(*permissions)
            .map {
                if (it) {
                    PermissionEvent.PermissionGranted
                } else {
                    PermissionEvent.RepeatPermissionRequest
                }
            }
}