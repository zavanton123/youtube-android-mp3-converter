package com.zavanton.yoump3.ui.main.fragment.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface MainFragmentMvpView : MvpView {

    fun showFullClipboard()

    fun showEmptyClipboard()

    fun startDownloadService()
}