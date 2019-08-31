package com.zavanton.yoump3.ui.splash.viewModel

interface ISplashActivityViewModel {

    fun processExtra(extra: String)

    fun checkPermissions()

    fun onPositiveButtonClick()

    fun onNegativeButtonClick()
}