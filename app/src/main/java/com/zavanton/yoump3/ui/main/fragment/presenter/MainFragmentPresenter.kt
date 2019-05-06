package com.zavanton.yoump3.ui.main.fragment.presenter

import com.zavanton.yoump3.di.scope.FragmentScope
import com.zavanton.yoump3.eventbus.EventBus
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

    private val eventBusDisposable = CompositeDisposable()

    init {
        Logger.d("MainFragmentPresenter is init")
        listenForEvents()
    }

    private fun listenForEvents() {
        eventBusDisposable.add(eventBus.listenForMessages()
            .subscribe {
                Logger.d("onNext message: ${it.text}")
            }
        )
    }

    override fun startDownloadService() {
        Logger.d("MainFragmentPresenter - startDownloadService")
        view?.startDownloadService()
    }

    override fun attach(IMainFragment: IMainFragment) {
        view = IMainFragment
    }

    override fun detach() {
        eventBusDisposable.clear()
        view = null
    }
}