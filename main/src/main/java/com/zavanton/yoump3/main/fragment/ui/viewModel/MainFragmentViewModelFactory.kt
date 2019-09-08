package com.zavanton.yoump3.main.fragment.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zavanton.yoump3.main.fragment.di.MainFragmentComponentManager
import javax.inject.Inject

class MainFragmentViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var viewModel: IMainFragmentViewModel

    init {
        MainFragmentComponentManager.getMainFragmentComponent()
            .inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel as T
}