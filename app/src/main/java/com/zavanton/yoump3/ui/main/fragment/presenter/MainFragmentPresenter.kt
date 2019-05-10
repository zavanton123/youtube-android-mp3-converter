package com.zavanton.yoump3.ui.main.fragment.presenter

import android.content.ClipboardManager
import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.eventbus.Event
import com.zavanton.yoump3.eventbus.EventBus
import com.zavanton.yoump3.eventbus.Message
import com.zavanton.yoump3.ui.main.fragment.view.IMainFragment
import com.zavanton.yoump3.utils.Logger
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
        Logger.d("MainFragmentPresenter is init")
        startListeningForClipboardChanges()
        startListeningForMessages()
    }

    override fun attach(mainFragment: IMainFragment) {
        view = mainFragment
    }

    override fun detach() {
        view = null
    }

    override fun startDownloadService() {
        Logger.d("startDownloadService")
        eventBus.send(Message(Event.DOWNLOAD_URL, actionUrl ?: clipboardUrl))
        view?.startDownloadService()
    }

    override fun onCleared() {
        eventBusDisposable.clear()
        clipboardManager.removePrimaryClipChangedListener(clipboardManagerListener)
    }

    private fun startListeningForClipboardChanges() {
        clipboardManagerListener = ClipboardManager.OnPrimaryClipChangedListener {
            val clipboardItem = clipboardManager.primaryClip?.getItemAt(0)
            Logger.d("checkClipboardAndProceed: $clipboardItem")

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
        eventBusDisposable.add(eventBus.listenForMessages()
            .subscribe {
                Logger.d("onNext message: ${it.text}")
                processMessage(it)
            }
        )
    }

    private fun processMessage(message: Message) {
        when (message.event) {
            Event.INTENT_ACTION_URL -> {
                Logger.d("INTENT_ACTION_URL - ${message.text}")
                actionUrl = checkUrl(message.text ?: "")
            }

            Event.CLIPBOARD_URL -> {
                Logger.d("CLIPBOARD_URL - ${message.text}")
                clipboardUrl = checkUrl(message.text ?: "")
            }

            Event.CLIPBOARD_EMPTY -> view?.showClipboardEmpty()
            Event.CLIPBOARD_NOT_EMPTY -> view?.showClipboardNotEmpty()
            Event.URL_INVALID -> {
                Logger.d("URL_INVALID - ${message.text}")

                view?.showUrlInvalid()
            }
            Event.URL_VALID -> {
                Logger.d("URL_VALID - ${message.text}")
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
                Logger.d("Received message - ${message.event} - ${message.text}")
            }
        }
    }

    private fun checkUrl(url: String): String? {
        Logger.d("checkUrl: $url")
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
        Logger.d("isUrlValid: $url")
        if (url.isEmpty()) return false

        if (url.contains("yout")) return true

        return false
    }
}