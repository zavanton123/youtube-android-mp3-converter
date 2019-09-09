package com.zavanton.yoump3.main.fragment.ui.viewModel

import android.content.ClipboardManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.core.di.FragmentScope
import com.zavanton.yoump3.download.business.model.Event
import com.zavanton.yoump3.download.eventBus.EventBus
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

    override fun listenForEvents() {
        eventBusDisposable.add(
            eventBus.listen()
                .subscribe { processMessage(it) }
        )
    }

    override fun onResume() {
        val url = getUrlFromClipboard()
        processClipboardUrl(url)
    }

    override fun onCleared() {
        Log.d()
        super.onCleared()

        eventBusDisposable.clear()
        MainFragmentComponentManager.clearMainFragmentComponent()
    }

    override fun startDownloadService() {
        eventBus.send(Event.SendDownloadUrl(actionUrl ?: clipboardUrl.orEmpty()))
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

    private fun processMessage(event: Event) {
        Log.i("$event")

        mainActionLiveData.value = when (event) {
            is Event.SendActionUrl -> getUrlActionFromIntent(event)
            is Event.DownloadStarted -> MainAction.ShowDownloadStarted
            is Event.DownloadProgress -> MainAction.ShowDownloadProgress(formatDownloadProgress(event.progress))

            Event.DownloadSuccess -> MainAction.ShowDownloadSuccess
            Event.DownloadError -> MainAction.ShowDownloadError
            Event.ConversionStarted -> MainAction.ShowConversionStarted
            is Event.ConversionProgress -> MainAction.ShowConversionProgress(event.progress)

            Event.ConversionSuccess -> MainAction.ShowConversionSuccess
            Event.ConversionError -> MainAction.ShowConversionError
            else -> MainAction.OtherAction
        }
    }

    private fun getUrlActionFromIntent(event: Event.SendActionUrl): MainAction =
        if (isUrlValid(event.url)) {
            actionUrl = event.url
            MainAction.ShowUrlValid
        } else {
            MainAction.ShowUrlInvalid
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