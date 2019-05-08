package com.zavanton.yoump3.ui.splash.presenter

import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.ui.splash.view.ISplashActivity

interface ISplashActivityPresenter {

    fun attach(view: ISplashActivity)
    fun detach()

    fun onViewCreated()
    fun processExtra(extra: String)

    fun checkPermissions(rxPermissions: RxPermissions)

    fun onPositiveButtonClick()
    fun onNegativeButtonClick()

    fun onCleared()
}
