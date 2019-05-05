package com.zavanton.yoump3.ui.demo

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.ui.demo.di.DemoProvidesModule
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject

class DemoViewModel : ViewModel() {

    @Inject
    lateinit var presenter: Demo.MvpPresenter

    init {
        Logger.d("DemoViewModel is init")
        TheApp.getAppComponent().plusDemoComponent(DemoProvidesModule())
            .inject(this)
    }
}