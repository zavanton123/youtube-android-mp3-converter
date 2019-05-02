package com.zavanton.yoump3.ui.main.fragment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zavanton.yoump3.ui.main.fragment.MainFragment
import javax.inject.Inject

class MainFragmentViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var viewModel: MainFragmentViewModel

    init {
        MainFragment.getMainFragmentComponent()?.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return viewModel as T
        } else {
            throw IllegalArgumentException("Wrong class")
        }
    }
}