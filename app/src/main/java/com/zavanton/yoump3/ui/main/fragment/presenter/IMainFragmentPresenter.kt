package com.zavanton.yoump3.ui.main.fragment.presenter

import com.zavanton.yoump3.ui.main.fragment.view.IMainFragment

interface IMainFragmentPresenter {

    fun startDownloadService()

    fun startListeningForMessages()
    fun stopListeningForMessages()

    fun attach(mainFragment: IMainFragment)
    fun detach()
}
