package com.zavanton.yoump3.ui.main.fragment.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.ui.main.fragment.di.manager.MainFragmentComponentManager
import com.zavanton.yoump3.ui.main.fragment.presenter.IMainFragmentPresenter
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

class MainFragmentViewModel : ViewModel() {

    @Inject
    lateinit var presenter: IMainFragmentPresenter

    init {
        Logger.d("MainFragmentViewModel is init")
        MainFragmentComponentManager.inject(this)
    }

    override fun onCleared() {
        Logger.d("MainFragmentViewModel - onCleared")
        super.onCleared()

        MainFragmentComponentManager.clear()
        presenter.onCleared()
    }
}