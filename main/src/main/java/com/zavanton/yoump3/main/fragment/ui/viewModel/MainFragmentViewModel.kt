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
import javax.inject.Inject

@FragmentScope
class MainFragmentViewModel @Inject constructor(
    private val eventBus: EventBus,
    private val clipboardManager: ClipboardManager
) : ViewModel(), IMainFragmentViewModel {

    override var mainActionLiveData: MutableLiveData<MainAction> = MutableLiveData()

    private var clipboardUrl: String? = null
    private var actionUrl: String? = null

    private var eventBusDisposable = CompositeDisposable()

    private lateinit var clipboardManagerListener: ClipboardManager.OnPrimaryClipChangedListener


    override fun onViewCreated() {
        Log.i("clipboardUrl: $clipboardUrl")
        Log.i("actionUrl: $actionUrl")
        startListeningForClipboardChanges()
        startListeningForMessages()
    }

    override fun onDestroyView() {
        Log.d()

        eventBusDisposable.clear()
        eventBusDisposable = CompositeDisposable()

        clipboardManager.removePrimaryClipChangedListener(clipboardManagerListener)
    }


    override fun startDownloadService() {
        Log.d()

        Log.i(
            "${Message(
                Event.DOWNLOAD_URL,
                actionUrl ?: clipboardUrl
            )}"
        )
        eventBus.send(
            Message(
                Event.DOWNLOAD_URL,
                actionUrl ?: clipboardUrl
            )
        )

        mainActionLiveData.value = MainAction.StartDownloadService
    }

    private fun startListeningForClipboardChanges() {
        Log.d()
        clipboardManagerListener = ClipboardManager.OnPrimaryClipChangedListener {
            val clipboardItem = clipboardManager.primaryClip?.getItemAt(0)
            Log.d("checkClipboardAndProceed: $clipboardItem")

            if (clipboardItem != null) {
                Log.i("${Message(Event.CLIPBOARD_NOT_EMPTY)}")
                eventBus.send(Message(Event.CLIPBOARD_NOT_EMPTY))

                Log.i(
                    "${Message(
                        Event.CLIPBOARD_URL,
                        clipboardItem.text.toString()
                    )}"
                )
                eventBus.send(
                    Message(
                        Event.CLIPBOARD_URL,
                        clipboardItem.text.toString()
                    )
                )
            } else {
                Log.i("${Message(Event.CLIPBOARD_EMPTY)}")
                eventBus.send(Message(Event.CLIPBOARD_EMPTY))
            }
        }
        clipboardManager.addPrimaryClipChangedListener(clipboardManagerListener)
    }

    private fun startListeningForMessages() {
        Log.i()
        eventBusDisposable.add(eventBus.listenForMessages()
            .subscribe {
                processMessage(it)
            }
        )
    }

    private fun processMessage(message: Message) {
        Log.i("$message")
        val action = when (message.event) {
            Event.INTENT_ACTION_URL -> getUrlActionFromIntent(message)
            Event.CLIPBOARD_URL -> getUrlActionFromClipboard(message)
            Event.CLIPBOARD_EMPTY -> MainAction.ShowClipboardEmpty
            Event.CLIPBOARD_NOT_EMPTY -> MainAction.ShowClipboardNotEmpty
            Event.URL_INVALID -> MainAction.ShowUrlInvalid
            Event.URL_VALID -> MainAction.ShowUrlValid
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

        mainActionLiveData.value = action
    }

    private fun getUrlActionFromClipboard(message: Message): MainAction =
        message.text.orEmpty().let { url ->
            if (isUrlValid(url)) {
                clipboardUrl = url
                MainAction.ShowUrlValid
            } else {
                MainAction.ShowUrlInvalid
            }
        }

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

    private fun getUrlActionFromIntent(message: Message): MainAction =
        message.text.orEmpty().let { url ->
            if (isUrlValid(url)) {
                assignUrl(message, url)
                MainAction.ShowUrlValid
            } else {
                MainAction.ShowUrlInvalid
            }
        }

    private fun assignUrl(message: Message, url: String) {
        when (message.event) {
            Event.CLIPBOARD_URL -> clipboardUrl = url
            Event.INTENT_ACTION_URL -> actionUrl = url
            else -> Log.d("other event")
        }
    }

    // TODO add network request to the url to check if response is ok
    // TODO find a better way to check youtube video url
    private fun isUrlValid(url: String): Boolean {
        Log.d("url: $url")
        if (url.isEmpty()) return false

        if (url.contains("yout")) return true

        return false
    }

    override fun onCleared() {
        Log.d()
        super.onCleared()

        MainFragmentComponentManager.clearMainFragmentComponent()
    }
}