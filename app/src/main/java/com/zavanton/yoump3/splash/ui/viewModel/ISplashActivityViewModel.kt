package com.zavanton.yoump3.splash.ui.viewModel

interface ISplashActivityViewModel {

    fun processExtra(extra: String)

    fun checkPermissions()

    fun onPositiveButtonClick()

    fun onNegativeButtonClick()
}