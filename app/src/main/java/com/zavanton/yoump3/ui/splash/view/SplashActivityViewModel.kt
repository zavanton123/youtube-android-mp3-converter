package com.zavanton.yoump3.ui.splash.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.di.ActivityScope
import com.zavanton.yoump3.eventbus.Event
import com.zavanton.yoump3.eventbus.EventBus
import com.zavanton.yoump3.eventbus.Message
import com.zavanton.yoump3.ui.splash.di.SplashActivityComponentManager
import com.zavanton.yoump3.utils.Log
import com.zavanton.yoump3.utils.Permissions
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScope
class SplashActivityViewModel @Inject constructor(
    private val eventBus: EventBus
) : ViewModel(), ISplashActivityViewModel {

    var splashEvent: MutableLiveData<SplashEvent> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    override fun processExtra(extra: String) {
        Log.d(extra)

        Log.i("${Message(Event.INTENT_ACTION_URL, extra)}")
        eventBus.send(Message(Event.INTENT_ACTION_URL, extra))
    }

    override fun checkPermissions(rxPermissions: RxPermissions) {
        Log.d("rxPermissions: $rxPermissions")
        compositeDisposable.add(
            rxPermissions.request(*Permissions.PERMISSIONS)
                .subscribe(
                    { arePermissionsGranted ->
                        if (arePermissionsGranted) {
                            splashEvent.value = SplashEvent.ProceedWithApp
                        } else {
                            splashEvent.value = SplashEvent.RepeatRequestPermissions
                        }
                    },
                    { Log.e(it, "An error occurred while checking permissions") }
                )
        )
    }

    override fun onPositiveButtonClick() {
        Log.d()
        splashEvent.value = SplashEvent.PositiveButtonClick
    }

    override fun onNegativeButtonClick() {
        Log.d()
        splashEvent.value = SplashEvent.NegativeButtonClick
    }

    override fun onCleared() {
        super.onCleared()
        Log.d()

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }

        SplashActivityComponentManager.clear()
    }
}