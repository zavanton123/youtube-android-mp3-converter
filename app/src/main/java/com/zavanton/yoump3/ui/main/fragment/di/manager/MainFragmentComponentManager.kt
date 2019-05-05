package com.zavanton.yoump3.ui.main.fragment.di.manager

import com.zavanton.yoump3.ui.main.activity.di.manager.MainActivityComponentManager
import com.zavanton.yoump3.ui.main.fragment.di.component.MainFragmentComponent
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentProvideModule
import com.zavanton.yoump3.ui.main.fragment.view.MainFragmentViewModel
import com.zavanton.yoump3.utils.Logger

object MainFragmentComponentManager {

    private var mainFragmentComponent: MainFragmentComponent? = null

    fun inject(mainFragmentViewModel: MainFragmentViewModel) {
        Logger.d("MainFragmentComponentManager - inject")

        mainFragmentComponent = MainActivityComponentManager.mainActivityComponent
            ?.plusMainFragmentComponent(MainFragmentProvideModule())

        mainFragmentComponent?.inject(mainFragmentViewModel)
    }

    fun clear() {
        Logger.d("MainFragmentComponentManager - clear")
        mainFragmentComponent = null
    }
}