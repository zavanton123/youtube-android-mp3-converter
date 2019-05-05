package com.zavanton.yoump3.ui.demo

interface Demo {

    interface MvpView

    interface MvpPresenter {

        fun attach(mvpView: MvpView)

        fun detach()
    }
}