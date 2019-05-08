package com.zavanton.yoump3.ui.splash.presenter

import com.zavanton.yoump3.ui.splash.view.ISplashActivity

interface ISplashActivityPresenter {

    fun attach(view: ISplashActivity)
    fun detach()

    fun processExtra(extra: String)
}
