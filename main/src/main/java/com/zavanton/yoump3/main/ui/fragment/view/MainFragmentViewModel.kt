package com.zavanton.yoump3.main.ui.fragment.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.main.ui.fragment.di.manager.MainFragmentComponentManager
import com.zavanton.yoump3.main.ui.fragment.presenter.IMainFragmentPresenter
import com.zavanton.yoump3.core.utils.Log
import javax.inject.Inject

class MainFragmentViewModel : ViewModel() {

    @Inject
    lateinit var presenter: IMainFragmentPresenter

    init {
        Log.d()
        MainFragmentComponentManager.inject(this)
    }

    override fun onCleared() {
        Log.d()
        super.onCleared()

        MainFragmentComponentManager.clear()
    }
}