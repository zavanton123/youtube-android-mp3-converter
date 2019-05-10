package com.zavanton.yoump3.ui.main.fragment.presenter

import android.content.ClipboardManager
import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.eventbus.Event
import com.zavanton.yoump3.eventbus.EventBus
import com.zavanton.yoump3.eventbus.Message
import com.zavanton.yoump3.ui.main.fragment.view.IMainFragment
import com.zavanton.yoump3.utils.Log
import io.reactivex.disposables.CompositeDisposable
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
        startListeningForClipboardChanges()
        startListeningForMessages()
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
        eventBus.send(Message(Event.DOWNLOAD_URL, actionUrl ?: clipboardUrl))
        view?.startDownloadService()
    }

    override fun onCleared() {
        Log.d()
        eventBusDisposable.clear()
        clipboardManager.removePrimaryClipChangedListener(clipboardManagerListener)
    }

    private fun startListeningForClipboardChanges() {
        Log.d()
        clipboardManagerListener = ClipboardManager.OnPrimaryClipChangedListener {
            val clipboardItem = clipboardManager.primaryClip?.getItemAt(0)
            Log.d("checkClipboardAndProceed: $clipboardItem")

            if (clipboardItem != null) {
                eventBus.send(Message(Event.CLIPBOARD_NOT_EMPTY))
                eventBus.send(Message(Event.CLIPBOARD_URL, clipboardItem.text.toString()))
            } else {
                eventBus.send(Message(Event.CLIPBOARD_EMPTY))
            }
        }
        clipboardManager.addPrimaryClipChangedListener(clipboardManagerListener)
    }

    private fun startListeningForMessages() {
        Log.d()
        eventBusDisposable.add(eventBus.listenForMessages()
            .subscribe {
                Log.d("onNext message: ${it.text}")
                processMessage(it)
            }
        )
    }

    private fun processMessage(message: Message) {
        Log.d("message: $message")
        when (message.event) {
            Event.INTENT_ACTION_URL -> {
                Log.d("INTENT_ACTION_URL - ${message.text}")
                actionUrl = checkUrl(message.text ?: "")
            }

            Event.CLIPBOARD_URL -> {
                Log.d("CLIPBOARD_URL - ${message.text}")
                clipboardUrl = checkUrl(message.text ?: "")
            }

            Event.CLIPBOARD_EMPTY -> view?.showClipboardEmpty()
            Event.CLIPBOARD_NOT_EMPTY -> view?.showClipboardNotEmpty()
            Event.URL_INVALID -> {
                Log.d("URL_INVALID - ${message.text}")

                view?.showUrlInvalid()
            }
            Event.URL_VALID -> {
                Log.d("URL_VALID - ${message.text}")
                view?.showUrlValid()
            }

            Event.DOWNLOAD_STARTED -> view?.showDownloadStarted()
            Event.DOWNLOAD_PROGRESS -> view?.showDownloadProgress(message.text)
            Event.DOWNLOAD_SUCCESS -> view?.showDownloadSuccess()
            Event.DOWNLOAD_ERROR -> view?.showDownloadError()

            Event.CONVERSION_STARTED -> view?.showConversionStarted()
            Event.CONVERSION_PROGRESS -> view?.showConversionProgress(message.text)
            Event.CONVERSION_SUCCESS -> view?.showConversionSuccess()
            Event.CONVERSION_ERROR -> view?.showConversionError()
            else -> {
                Log.d("Received message - ${message.event} - ${message.text}")
            }
        }
    }

    private fun checkUrl(url: String): String? {
        Log.d("url: $url")
        return if (isUrlValid(url)) {
            eventBus.send(Message(Event.URL_VALID))
            url
        } else {
            eventBus.send(Message(Event.URL_INVALID))
            null
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