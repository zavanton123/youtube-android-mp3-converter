package com.zavanton.yoump3.main.fragment.ui.presenter

import com.zavanton.yoump3.main.fragment.ui.view.IMainFragment

interface IMainFragmentPresenter {

    fun onViewCreated()
    fun onDestroyView()

    fun attach(mainFragment: IMainFragment)
    fun detach()

    fun startDownloadService()
}
