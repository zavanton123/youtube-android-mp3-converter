package com.zavanton.yoump3.ui.main.fragment.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.ui.main.fragment.view.MainFragmentMvpView
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

@FragmentScope
@InjectViewState
class MainFragmentPresenter
@Inject
constructor() : MvpPresenter<MainFragmentMvpView>() {

    init {
        Logger.d("MainFragmentPresenter is init")
        viewState.showEmptyClipboard()
    }

    fun startDownloadService() {
        Logger.d("MainFragmentPresenter - startDownloadService")
        viewState.startDownloadService()
    }
}