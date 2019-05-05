package com.zavanton.yoump3.ui.main.fragment.presenter

import com.zavanton.yoump3.ui.main.fragment.view.IMainFragment

interface IMainFragmentPresenter {

    fun attach(IMainFragment: IMainFragment)
    fun detach()

    fun startDownloadService()
}