package com.zavanton.yoump3.ui.splash.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.ui.splash.di.manager.SplashActivityComponentManager
import com.zavanton.yoump3.ui.splash.presenter.ISplashActivityPresenter
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

class SplashActivityViewModel : ViewModel() {

    @Inject
    lateinit var presenter: ISplashActivityPresenter

    init {
        Logger.d("SplashActivityViewModel is init")
        SplashActivityComponentManager.inject(this)
    }

    override fun onCleared() {
        Logger.d("SplashActivityViewModel - onCleared()")
        super.onCleared()

        SplashActivityComponentManager.clear()
    }
}