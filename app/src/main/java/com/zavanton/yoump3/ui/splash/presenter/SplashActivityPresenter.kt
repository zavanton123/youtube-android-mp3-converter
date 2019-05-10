package com.zavanton.yoump3.ui.splash.presenter

import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.eventbus.Event
import com.zavanton.yoump3.eventbus.EventBus
import com.zavanton.yoump3.eventbus.Message
import com.zavanton.yoump3.ui.splash.view.ISplashActivity
import com.zavanton.yoump3.utils.Logger
import com.zavanton.yoump3.utils.Permissions
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScope
class SplashActivityPresenter
@Inject
constructor(private val eventBus: EventBus) : ISplashActivityPresenter {

    private var view: ISplashActivity? = null

    private val compositeDisposable = CompositeDisposable()

    init {
        Logger.d("SplashActivityPresenter is init")
    }

    override fun attach(view: ISplashActivity) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override fun onViewCreated() {
        Logger.d("SplashActivityPresenter - onViewCreated")

        view?.processIntentExtras()
    }

    override fun processExtra(extra: String) {
        Logger.d("SplashActivityPresenter - processExtra: $extra")
        eventBus.send(Message(Event.INTENT_ACTION_URL, extra))
    }

    override fun checkPermissions(rxPermissions: RxPermissions) {
        compositeDisposable.add(
            rxPermissions.request(*Permissions.PERMISSIONS)
                .subscribe(
                    { arePermissionsGranted ->
                        if (arePermissionsGranted) {
                            view?.goToMainActivity()
                        } else {
                            view?.repeatRequestPermissions()
                        }
                    },
                    { Logger.e("An error occurred while checking permissions", it) }
                )
        )
    }

    override fun onPositiveButtonClick() {
        view?.onPositiveButtonClick()
    }

    override fun onNegativeButtonClick() {
        view?.onNegativeButtonClick()
    }

    override fun onCleared() {
        Logger.d("SplashActivityPresenter - onCleared")
        compositeDisposable.dispose()
    }
}