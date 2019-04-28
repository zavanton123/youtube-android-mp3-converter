package com.zavanton.yoump3.ui.main.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFragmentViewModel : ViewModel() {

    private val isClipboardFull = MutableLiveData<Boolean>()

    fun init() {

        isClipboardFull.value = false
    }

    fun getClipboardStatus() = isClipboardFull
}