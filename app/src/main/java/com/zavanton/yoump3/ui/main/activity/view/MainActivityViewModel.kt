package com.zavanton.yoump3.ui.main.activity.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.ui.main.activity.di.MainActivityComponentManager
import com.zavanton.yoump3.utils.Log

class MainActivityViewModel : ViewModel() {

    init {
        Log.d()
        MainActivityComponentManager.inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d()

        MainActivityComponentManager.clear()
    }
}


