package com.zavanton.yoump3.ui.main.fragment.presenter

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
    private val eventBus: EventBus
) : IMainFragmentPresenter {

    var view: IMainFragment? = null

    private var eventBusDisposable = CompositeDisposable()

    init {
        Logger.d("MainFragmentPresenter is init")
    }

    override fun startListeningForMessages() {
        eventBusDisposable.add(eventBus.listenForMessages()
            .subscribe {
                Logger.d("onNext message: ${it.text}")
                processMessage(it)
            }
        )
    }

    override fun stopListeningForMessages() {
        eventBusDisposable.clear()
        eventBusDisposable = CompositeDisposable()
    }

    private fun processMessage(message: Message) {
        when (message.event) {
            Event.CLIPBOARD_EMPTY -> view?.showClipboardEmpty()
            Event.CLIPBOARD_NOT_EMPTY -> view?.showClipboardNotEmpty()

            Event.URL_INVALID -> view?.showUrlInvalid()
            Event.URL_VALID -> view?.showUrlValid()

            Event.DOWNLOAD_STARTED -> view?.showDownloadStarted()
            Event.DOWNLOAD_PROGRESS -> view?.showDownloadProgress(message.text)
            Event.DOWNLOAD_SUCCESS -> view?.showDownloadSuccess()
            Event.DOWNLOAD_ERROR -> view?.showDownloadError()

            Event.CONVERSION_STARTED -> view?.showConversionStarted()
            Event.CONVERSION_PROGRESS -> view?.showConversionProgress(message.text)
            Event.CONVERSION_SUCCESS -> view?.showConversionSuccess()
            Event.CONVERSION_ERROR -> view?.showConversionError()
        }
    }

    override fun startDownloadService() {
        Logger.d("MainFragmentPresenter - startDownloadService")
        view?.startDownloadService()
    }

    override fun attach(mainFragment: IMainFragment) {
        view = mainFragment
    }

    override fun detach() {
        view = null
    }
}