package com.zavanton.yoump3.ui.main.fragment.presenter

import android.content.ClipboardManager
import com.zavanton.yoump3.core.di.FragmentScope
import com.zavanton.yoump3.eventbus.Event
import com.zavanton.yoump3.eventbus.EventBus
import com.zavanton.yoump3.eventbus.Message
import com.zavanton.yoump3.ui.main.fragment.view.IMainFragment
import com.zavanton.yoump3.core.utils.Log
import io.reactivex.disposables.CompositeDisposable
import java.text.DecimalFormat
import javax.inject.Inject

@FragmentScope
class MainFragmentPresenter
@Inject
constructor(
    private val eventBus: EventBus,
    private val clipboardManager: ClipboardManager
) : IMainFragmentPresenter {

    var view: IMainFragment? = null

    private var clipboardUrl: String? = null
    private var actionUrl: String? = null

    private var eventBusDisposable = CompositeDisposable()

    private lateinit var clipboardManagerListener: ClipboardManager.OnPrimaryClipChangedListener

    init {
        Log.d()

    }

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

    override fun attach(mainFragment: IMainFragment) {
        Log.d()
        view = mainFragment
    }

    override fun detach() {
        Log.d()
        view = null
    }

    override fun startDownloadService() {
        Log.d()

        Log.i("${Message(Event.DOWNLOAD_URL, actionUrl ?: clipboardUrl)}")
        eventBus.send(Message(Event.DOWNLOAD_URL, actionUrl ?: clipboardUrl))

        view?.startDownloadService()
    }

    private fun startListeningForClipboardChanges() {
        Log.d()
        clipboardManagerListener = ClipboardManager.OnPrimaryClipChangedListener {
            val clipboardItem = clipboardManager.primaryClip?.getItemAt(0)
            Log.d("checkClipboardAndProceed: $clipboardItem")

            if (clipboardItem != null) {
                Log.i("${Message(Event.CLIPBOARD_NOT_EMPTY)}")
                eventBus.send(Message(Event.CLIPBOARD_NOT_EMPTY))

                Log.i("${Message(Event.CLIPBOARD_URL, clipboardItem.text.toString())}")
                eventBus.send(Message(Event.CLIPBOARD_URL, clipboardItem.text.toString()))
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
        when (message.event) {
            Event.INTENT_ACTION_URL -> processMessageWithUrl(message)

            Event.CLIPBOARD_URL -> {

                val url = message.text ?: ""
                if (isUrlValid(url)) {
                    clipboardUrl = url
                    view?.showUrlValid()
                } else {
                    view?.showUrlInvalid()
                }
            }

            Event.CLIPBOARD_EMPTY -> view?.showClipboardEmpty()
            Event.CLIPBOARD_NOT_EMPTY -> view?.showClipboardNotEmpty()
            Event.URL_INVALID -> view?.showUrlInvalid()
            Event.URL_VALID -> view?.showUrlValid()

            Event.DOWNLOAD_STARTED -> view?.showDownloadStarted()
            Event.DOWNLOAD_PROGRESS -> showDownloadProgress(message)
            Event.DOWNLOAD_SUCCESS -> view?.showDownloadSuccess()
            Event.DOWNLOAD_ERROR -> view?.showDownloadError()

            Event.CONVERSION_STARTED -> view?.showConversionStarted()
            Event.CONVERSION_PROGRESS -> view?.showConversionProgress(message.text)
            Event.CONVERSION_SUCCESS -> view?.showConversionSuccess()
            Event.CONVERSION_ERROR -> view?.showConversionError()

            else -> Log.i("Other event received")
        }
    }

    private fun showDownloadProgress(message: Message) {
        Log.d()
        message.text?.apply {
            view?.showDownloadProgress(formatDownloadProgress())
        }
    }

    private fun String.formatDownloadProgress(): String? {
        Log.d()
        val progress = Integer.valueOf(this)
        val progressInKB = (progress / 1024).toDouble()
        val progressInMB = progressInKB / 1024
        val format = DecimalFormat("#.##")
        return format.format(progressInMB)
    }

    private fun processMessageWithUrl(message: Message) {
        val url = message.text ?: ""
        if (isUrlValid(url)) {
            view?.showUrlValid()
            assignUrl(message, url)
        } else {
            view?.showUrlInvalid()
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
}