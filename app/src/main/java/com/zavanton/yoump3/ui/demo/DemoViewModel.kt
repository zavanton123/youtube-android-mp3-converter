package com.zavanton.yoump3.ui.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.ui.demo.di.DemoModule
import javax.inject.Inject

class DemoViewModel : ViewModel() {

    @Inject
    lateinit var presenter: Demo.MvpPresenter

    init {
        TheApp.getAppComponent().plusDemoComponent(DemoModule())
            .inject(this)
    }

    class Factory : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(DemoViewModel::class.java)) {
                modelClass as T
            } else {
                throw IllegalArgumentException("ViewModel not found")
            }
        }
    }
}