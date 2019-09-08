package com.zavanton.yoump3.main.activity.ui

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.main.activity.di.MainActivityComponentManager

class MainActivityViewModel : ViewModel() {

    init {
        Log.d()
        MainActivityComponentManager.getMainActivityComponent()
            .inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d()

        MainActivityComponentManager.clearMainActivityComponent()
    }
}


