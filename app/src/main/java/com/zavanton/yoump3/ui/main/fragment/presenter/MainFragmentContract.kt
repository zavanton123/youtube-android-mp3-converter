package com.zavanton.yoump3.ui.main.fragment.presenter

interface MainFragmentContract {

    interface MvpView {

        fun showFullClipboard()
        fun showEmptyClipboard()

        fun startDownloadService()
    }

    interface MvpPresenter {

        fun attach(mvpView: MvpView)
        fun detach()

        fun startDownloadService()
    }
}