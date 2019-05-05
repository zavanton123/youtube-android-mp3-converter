package com.zavanton.yoump3.ui.main.activity.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.ui.main.activity.di.manager.MainActivityComponentManager
import com.zavanton.yoump3.utils.Logger

class MainActivityViewModel : ViewModel() {

    init {
        Logger.d("MainActivityViewModel is init")
        MainActivityComponentManager.inject(this)
    }

    override fun onCleared() {
        super.onCleared()

        Logger.d("MainActivityViewModel - onCleared")
        MainActivityComponentManager.clear()
    }
}


