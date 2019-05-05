package com.zavanton.yoump3.ui.download.presenter

interface DownloadContract {

    interface MvpView {

        fun startForeground()
        fun stopForeground()
    }

    interface MvpPresenter {

        fun bind(mvpView: MvpView)
        fun unbind(mvpView: MvpView)

        fun onStartCommand()
    }
}