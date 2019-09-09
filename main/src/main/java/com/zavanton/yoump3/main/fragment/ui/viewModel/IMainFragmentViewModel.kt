package com.zavanton.yoump3.main.fragment.ui.viewModel

import androidx.lifecycle.MutableLiveData

interface IMainFragmentViewModel {

    val mainActionLiveData: MutableLiveData<MainAction>

    fun listenForEvents()
    fun onResume()

    fun startDownloadService()
}
