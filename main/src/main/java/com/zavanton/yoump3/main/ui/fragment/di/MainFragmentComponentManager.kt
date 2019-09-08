package com.zavanton.yoump3.main.ui.fragment.di

import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.main.di.MainActivityComponentManager
import com.zavanton.yoump3.main.ui.fragment.view.MainFragmentViewModel

object MainFragmentComponentManager {

    private var mainFragmentComponent: MainFragmentComponent? = null

    fun inject(mainFragmentViewModel: MainFragmentViewModel) {
        Log.d()

        mainFragmentComponent = MainActivityComponentManager.getMainActivityComponent()
            .plusMainFragmentComponent(MainFragmentProvideModule())

        mainFragmentComponent?.inject(mainFragmentViewModel)
    }

    fun clear() {
        Log.d()
        mainFragmentComponent = null
    }
}