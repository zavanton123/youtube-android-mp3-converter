package com.zavanton.yoump3.ui.splash.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zavanton.yoump3.ui.splash.di.SplashActivityComponentManager
import javax.inject.Inject

class SplashActivityViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var viewModel: ISplashActivityViewModel

    init {
        SplashActivityComponentManager.get()
            ?.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel as T
}