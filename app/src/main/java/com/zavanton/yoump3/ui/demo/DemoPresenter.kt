package com.zavanton.yoump3.ui.demo

import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

@ActivityScope
class DemoPresenter
@Inject
constructor() : Demo.MvpPresenter {

    private var view: Demo.MvpView? = null

    init {
        Logger.d("DemoPresenter is init")
    }

    override fun attach(mvpView: Demo.MvpView) {
        this.view = mvpView
    }

    override fun detach(mvpView: Demo.MvpView) {
        view = null
    }
}