package com.zavanton.yoump3.ui.main.activity.presenter

import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

@ActivityScope
class MainActivityPresenter
@Inject
constructor() : IMainActivityPresenter {

    init {
        Logger.d("MainActivityPresenter is init")
    }

    override fun onCreated() {
        Logger.d("MainActivityPresenter onCreated")
    }
}