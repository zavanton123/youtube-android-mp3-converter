package com.zavanton.yoump3.ui.viewModel

sealed class SplashEvent {

    object ProceedWithApp : SplashEvent()
    object RepeatRequestPermissions : SplashEvent()
    object PositiveButtonClick : SplashEvent()
    object NegativeButtonClick : SplashEvent()
}