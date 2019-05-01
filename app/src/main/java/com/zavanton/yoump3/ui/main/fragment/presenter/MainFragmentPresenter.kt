package com.zavanton.yoump3.ui.main.fragment.presenter

import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

@FragmentScope
class MainFragmentPresenter
@Inject
constructor() : IMainFragmentPresenter {
    init {
        Logger.d("MainFragmentPresenter - init")
    }

    override fun onViewCreated() {
        Logger.d("MainFragmentPresenter - onViewCreated")
    }
}