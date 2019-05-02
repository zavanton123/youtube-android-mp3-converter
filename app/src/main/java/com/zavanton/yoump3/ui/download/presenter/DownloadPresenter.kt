package com.zavanton.yoump3.ui.download.presenter

import com.zavanton.yoump3.di.scope.ServiceScope
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

@ServiceScope
class DownloadPresenter
@Inject
constructor() : IDownloadPresenter {

    init {
        Logger.d("DownloadPresenter is init")
    }

    override fun onStartCommand() {
        Logger.d("DownloadPresenter - onStartCommand")
    }
}