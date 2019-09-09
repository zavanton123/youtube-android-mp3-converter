package com.zavanton.yoump3.main.fragment.ui.viewModel

sealed class MainAction {

    object ShowClipboardNotEmpty : MainAction()
    object ShowClipboardEmpty : MainAction()

    object ShowUrlValid : MainAction()
    object ShowUrlInvalid : MainAction()

    object StartDownloadService : MainAction()
    object ShowDownloadStarted : MainAction()
    data class ShowDownloadProgress(val downloadProgress: String) : MainAction()
    object ShowDownloadSuccess : MainAction()
    object ShowDownloadError : MainAction()

    object ShowConversionStarted : MainAction()
    data class ShowConversionProgress(val conversionProgress: String) : MainAction()
    object ShowConversionSuccess : MainAction()
    object ShowConversionError : MainAction()

    object OtherAction : MainAction()
}