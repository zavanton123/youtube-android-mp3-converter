package com.zavanton.yoump3.ui.main.fragment.presenter

import com.zavanton.yoump3.ui.main.fragment.view.IMainFragment

interface IMainFragmentPresenter {

    fun onViewCreated()

    fun attach(mainFragment: IMainFragment)
    fun detach()

    fun startDownloadService()

    fun onCleared()
}
