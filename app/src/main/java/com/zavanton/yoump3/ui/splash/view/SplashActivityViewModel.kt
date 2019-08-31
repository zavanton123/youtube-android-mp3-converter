package com.zavanton.yoump3.ui.splash.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.ui.splash.di.manager.SplashActivityComponentManager
import com.zavanton.yoump3.ui.splash.presenter.ISplashActivityPresenter
import com.zavanton.yoump3.utils.Log
import javax.inject.Inject

class SplashActivityViewModel : ViewModel() {

    @Inject
    lateinit var presenter: ISplashActivityPresenter

    init {
        Log.d()
        SplashActivityComponentManager.inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d()

        presenter.onCleared()
        SplashActivityComponentManager.clear()
    }
}