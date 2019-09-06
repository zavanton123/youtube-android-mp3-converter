package com.zavanton.yoump3.ui.viewModel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zavanton.yoump3.di.SplashActivityComponentManager
import javax.inject.Inject

class SplashActivityViewModelFactory(
    activity: FragmentActivity
) : ViewModelProvider.Factory {

    @Inject
    lateinit var viewModel: ISplashActivityViewModel

    init {
        SplashActivityComponentManager.get(activity)
            ?.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel as T
}