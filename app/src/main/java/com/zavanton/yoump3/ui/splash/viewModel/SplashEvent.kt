package com.zavanton.yoump3.ui.splash.viewModel

sealed class SplashEvent {

    object ProceedWithApp : SplashEvent()
    object RepeatRequestPermissions : SplashEvent()
    object PositiveButtonClick : SplashEvent()
    object NegativeButtonClick : SplashEvent()
}