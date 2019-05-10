package com.zavanton.yoump3.ui.main.fragment.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.ui.main.fragment.di.manager.MainFragmentComponentManager
import com.zavanton.yoump3.ui.main.fragment.presenter.IMainFragmentPresenter
import com.zavanton.yoump3.utils.Log
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