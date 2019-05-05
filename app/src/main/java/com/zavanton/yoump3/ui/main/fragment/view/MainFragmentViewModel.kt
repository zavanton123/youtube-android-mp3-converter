package com.zavanton.yoump3.ui.main.fragment.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.ui.main.activity.view.MainActivity
import com.zavanton.yoump3.ui.main.activity.view.MainActivityViewModel
import com.zavanton.yoump3.ui.main.fragment.di.component.MainFragmentComponent
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentProvideModule
import com.zavanton.yoump3.ui.main.fragment.presenter.MainFragmentContract
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

class MainFragmentViewModel : ViewModel() {

    @Inject
    lateinit var presenter: MainFragmentContract.MvpPresenter

    private var mainFragmentComponent: MainFragmentComponent? = null

    init {
        Logger.d("MainFragmentViewModel is init")

        mainFragmentComponent = MainActivityViewModel.mainActivityComponent
            ?.plusMainFragmentComponent(MainFragmentProvideModule())
            ?.apply {
                inject(this@MainFragmentViewModel)
            }
    }

    override fun onCleared() {
        Logger.d("MainFragmentViewModel - onCleared")
        super.onCleared()

        mainFragmentComponent = null
    }
}