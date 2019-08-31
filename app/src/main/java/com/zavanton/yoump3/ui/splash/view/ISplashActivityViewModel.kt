package com.zavanton.yoump3.ui.splash.view

import com.tbruyelle.rxpermissions2.RxPermissions

interface ISplashActivityViewModel {

    fun processExtra(extra: String)

    fun checkPermissions(rxPermissions: RxPermissions)

    fun onPositiveButtonClick()

    fun onNegativeButtonClick()
}