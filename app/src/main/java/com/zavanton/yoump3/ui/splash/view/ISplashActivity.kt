package com.zavanton.yoump3.ui.splash.view

interface ISplashActivity {

    fun processIntentExtras()

    fun goToMainActivity()
    fun repeatRequestPermissions()

    fun onPositiveButtonClick()
    fun onNegativeButtonClick()
}
