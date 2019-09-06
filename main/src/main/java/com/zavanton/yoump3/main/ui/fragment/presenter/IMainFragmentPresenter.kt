package com.zavanton.yoump3.main.ui.fragment.presenter

import com.zavanton.yoump3.main.ui.fragment.view.IMainFragment

interface IMainFragmentPresenter {

    fun onViewCreated()
    fun onDestroyView()

    fun attach(mainFragment: IMainFragment)
    fun detach()

    fun startDownloadService()
}
