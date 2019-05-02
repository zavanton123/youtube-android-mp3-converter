package com.zavanton.yoump3.ui.main.fragment.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.di.qualifier.context.ApplicationContext
import com.zavanton.yoump3.ui.download.service.DownloadService
import javax.inject.Inject

class MainFragmentViewModel
@Inject
constructor(
    @ApplicationContext
    private val appContext: Context
) : ViewModel() {

    val isClipboardFull = MutableLiveData<Boolean>()

    fun init() {
        isClipboardFull.value = true
    }

    fun startDownloadService() {
        Intent(appContext, DownloadService::class.java).apply {
            appContext.startService(this)
        }
    }
}