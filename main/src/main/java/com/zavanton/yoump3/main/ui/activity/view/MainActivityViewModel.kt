package com.zavanton.yoump3.main.ui.activity.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.main.di.MainActivityComponentManager
import com.zavanton.yoump3.core.utils.Log

class MainActivityViewModel : ViewModel() {

    init {
        Log.d()
        MainActivityComponentManager.getMainActivityComponent().inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d()

        MainActivityComponentManager.clear()
    }
}


