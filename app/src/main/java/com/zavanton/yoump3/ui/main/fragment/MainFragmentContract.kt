package com.zavanton.yoump3.ui.main.fragment

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