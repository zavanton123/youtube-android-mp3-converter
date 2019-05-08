package com.zavanton.yoump3.ui.splash.presenter

import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.ui.splash.view.ISplashActivity
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

@ActivityScope
class SplashActivityPresenter
@Inject
constructor() : ISplashActivityPresenter {

    private var view: ISplashActivity? = null

    init {
        Logger.d("SplashActivityPresenter is init")
    }

    override fun attach(view: ISplashActivity) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override fun onViewCreated() {
        Logger.d("SplashActivityPresenter - onViewCreated")

        view?.processIntentExtras()
        view?.checkPermissionsAndStartApp()
    }

    override fun processExtra(extra: String) {
        Logger.d("SplashActivityPresenter - processExtra: $extra")
    }
}