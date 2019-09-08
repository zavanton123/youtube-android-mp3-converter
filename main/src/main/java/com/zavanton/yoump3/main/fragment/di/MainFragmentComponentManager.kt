package com.zavanton.yoump3.main.fragment.di

import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.main.activity.di.MainActivityComponentManager

object MainFragmentComponentManager {

    private var mainFragmentComponent: MainFragmentComponent? = null

    fun getMainFragmentComponent(): MainFragmentComponent =
        mainFragmentComponent ?: MainActivityComponentManager
            .getMainActivityComponent()
            .plusMainFragmentComponent(MainFragmentProvideModule())
            .also {
                mainFragmentComponent = it
            }

    fun clearMainFragmentComponent() {
        Log.d()
        mainFragmentComponent = null
    }
}