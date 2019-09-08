package com.zavanton.yoump3.main.fragment.ui.viewModel

import android.content.ClipboardManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.core.di.FragmentScope
import com.zavanton.yoump3.core.eventBus.Event
import com.zavanton.yoump3.core.eventBus.EventBus
import com.zavanton.yoump3.core.eventBus.Message
import com.zavanton.yoump3.core.utils.Constants.EMPTY_STRING
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.main.fragment.di.MainFragmentComponentManager
import io.reactivex.disposables.CompositeDisposable
import java.text.DecimalFormat
import java.util.regex.Pattern
import javax.inject.Inject

@FragmentScope
class MainFragmentViewModel @Inject constructor(
    private val eventBus: EventBus,
    private val clipboardManager: ClipboardManager
) : ViewModel(), IMainFragmentViewModel {

    companion object {

        private const val VALID_URL_REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
    }

    override var mainActionLiveData: MutableLiveData<MainAction> = MutableLiveData()

    private var clipboardUrl: String? = null
    private var actionUrl: String? = null
    private val urlPattern: Pattern = Pattern.compile(VALID_URL_REGEX)

    private var eventBusDisposable = CompositeDisposable()

    override fun onViewCreated() {
        eventBusDisposable.add(
            eventBus.listenForMessages()
                .subscribe { processMessage(it) }
        )
    }

    override fun onResume() {
        val url = getUrlFromClipboard()
        Log.d("the url from clipboard is $url")

        processClipboardUrl(url)
    }

    override fun onDestroyView() {
        Log.d()

        eventBusDisposable.clear()
        eventBusDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        Log.d()
        super.onCleared()

        MainFragmentComponentManager.clearMainFragmentComponent()
    }

    override fun startDownloadService() {
        Log.d()
        eventBus.send(
            Message(
                Event.DOWNLOAD_URL,
                actionUrl ?: clipboardUrl
            )
        )

        mainActionLiveData.value = MainAction.StartDownloadService
    }

    private fun getUrlFromClipboard(): String =
        clipboardManager.primaryClip
            ?.getItemAt(0)
            ?.text
            ?.toString()
            ?: EMPTY_STRING

    private fun processClipboardUrl(url: String) {
        mainActionLiveData.value =
            when {
                url.isEmpty() -> MainAction.ShowClipboardEmpty
                isUrlValid(url) -> MainAction.ShowUrlValid.also { clipboardUrl = url }
                else -> MainAction.ShowUrlInvalid
            }
    }

    private fun processMessage(message: Message) {
        Log.i("$message")

        mainActionLiveData.value = when (message.event) {
            Event.INTENT_ACTION_URL -> getUrlActionFromIntent(message)
            Event.DOWNLOAD_STARTED -> MainAction.ShowDownloadStarted
            Event.DOWNLOAD_PROGRESS -> MainAction.ShowDownloadProgress(
                formatDownloadProgress(message.text.orEmpty())
            )
            Event.DOWNLOAD_SUCCESS -> MainAction.ShowDownloadSuccess
            Event.DOWNLOAD_ERROR -> MainAction.ShowDownloadError
            Event.CONVERSION_STARTED -> MainAction.ShowConversionStarted
            Event.CONVERSION_PROGRESS -> MainAction.ShowConversionProgress(
                message.text.orEmpty()
            )
            Event.CONVERSION_SUCCESS -> MainAction.ShowConversionSuccess
            Event.CONVERSION_ERROR -> MainAction.ShowConversionError
            else -> MainAction.OtherAction
        }
    }

    private fun getUrlActionFromIntent(message: Message): MainAction =
        message.text.orEmpty().let { url ->
            if (isUrlValid(url)) {
                actionUrl = url
                MainAction.ShowUrlValid
            } else {
                MainAction.ShowUrlInvalid
            }
        }

    private fun isUrlValid(url: String) = urlPattern.matcher(url).matches()

    private fun formatDownloadProgress(value: String): String =
        try {
            val progress = Integer.valueOf(value)
            val progressInKB = (progress / 1024).toDouble()
            val progressInMB = progressInKB / 1024
            val format = DecimalFormat("#.##")
            format.format(progressInMB)
        } catch (ex: Exception) {
            Log.e(ex)
            EMPTY_STRING
        }
}