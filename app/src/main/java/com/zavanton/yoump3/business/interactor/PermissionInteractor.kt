package com.zavanton.yoump3.business.interactor

import android.Manifest
import com.zavanton.yoump3.business.model.PermissionEvent
import com.zavanton.yoump3.core.di.ActivityScope
import com.zavanton.yoump3.data.IPermissionService
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class PermissionInteractor @Inject constructor(
    private val permissionService: IPermissionService
) : IPermissionInteractor {

    companion object {

        val PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun checkPermissions(): Observable<PermissionEvent> =
        permissionService.checkPermissions(PERMISSIONS)
}