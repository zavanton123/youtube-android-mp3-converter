package com.zavanton.yoump3.ui.main.fragment.presenter

import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

@FragmentScope
class MainFragmentPresenter
@Inject
constructor() : MainFragmentContract.MvpPresenter {

    var view: MainFragmentContract.MvpView? = null

    init {
        Logger.d("MainFragmentPresenter is init")
        view?.showEmptyClipboard()
    }

    override fun startDownloadService() {
        Logger.d("MainFragmentPresenter - startDownloadService")
        view?.startDownloadService()
    }

    override fun attach(mvpView: MainFragmentContract.MvpView) {
        view = mvpView
    }

    override fun detach() {
        view = null
    }
}