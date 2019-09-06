package com.zavanton.yoump3.ui.main.fragment.di.manager

import com.zavanton.yoump3.ui.main.activity.di.MainActivityComponentManager
import com.zavanton.yoump3.ui.main.fragment.di.component.MainFragmentComponent
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentProvideModule
import com.zavanton.yoump3.ui.main.fragment.view.MainFragmentViewModel
import com.zavanton.yoump3.core.utils.Log

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