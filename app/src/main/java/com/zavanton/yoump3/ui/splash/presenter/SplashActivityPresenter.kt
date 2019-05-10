package com.zavanton.yoump3.ui.splash.presenter

import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.eventbus.Event
import com.zavanton.yoump3.eventbus.EventBus
import com.zavanton.yoump3.eventbus.Message
import com.zavanton.yoump3.ui.splash.view.ISplashActivity
import com.zavanton.yoump3.utils.Log
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
        Log.d()
    }

    override fun attach(view: ISplashActivity) {
        Log.d()
        this.view = view
    }

    override fun detach() {
        Log.d()
        this.view = null
    }

    override fun onViewCreated() {
        Log.d()

        view?.processIntentExtras()
    }

    override fun processExtra(extra: String) {
        Log.d("extra: $extra")
        eventBus.send(Message(Event.INTENT_ACTION_URL, extra))
    }

    override fun checkPermissions(rxPermissions: RxPermissions) {
        Log.d("rxPermissions: $rxPermissions")
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
                    { Log.e(it, "An error occurred while checking permissions") }
                )
        )
    }

    override fun onPositiveButtonClick() {
        Log.d()
        view?.onPositiveButtonClick()
    }

    override fun onNegativeButtonClick() {
        Log.d()
        view?.onNegativeButtonClick()
    }

    override fun onCleared() {
        Log.d()
        compositeDisposable.dispose()
    }
}