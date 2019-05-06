package com.zavanton.yoump3.ui.main.fragment.view

interface IMainFragment {

    fun showClipboardNotEmpty()
    fun showClipboardEmpty()

    fun showUrlValid()
    fun showUrlInvalid()

    fun startDownloadService()
    fun showDownloadStarted()
    fun showDownloadProgress(downloadProgress: String?)
    fun showDownloadSuccess()
    fun showDownloadError()

    fun showConversionStarted()
    fun showConversionProgress(conversionProgress: String?)
    fun showConversionSuccess()
    fun showConversionError()
}