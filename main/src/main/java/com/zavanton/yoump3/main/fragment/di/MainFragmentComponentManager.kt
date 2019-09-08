package com.zavanton.yoump3.main.fragment.di

import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.main.activity.di.MainActivityComponentManager
import com.zavanton.yoump3.main.fragment.ui.view.MainFragmentViewModel

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