package com.zavanton.yoump3.splash.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.business.interactor.IPermissionInteractor
import com.zavanton.yoump3.business.model.PermissionEvent
import com.zavanton.yoump3.core.di.ActivityScope
import com.zavanton.yoump3.core.eventBus.Event
import com.zavanton.yoump3.core.eventBus.EventBus
import com.zavanton.yoump3.core.eventBus.Message
import com.zavanton.yoump3.core.utils.Log
import com.zavanton.yoump3.splash.di.SplashActivityComponentManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScope
class SplashActivityViewModel @Inject constructor(
    private val eventBus: EventBus,
    private val permissionsInteractor: IPermissionInteractor
) : ViewModel(), ISplashActivityViewModel {

    var permissionEvent: MutableLiveData<PermissionEvent> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    override fun processIntentExtra(extra: String) {
        eventBus.send(Message(Event.INTENT_ACTION_URL, extra))
    }

    override fun checkPermissions() {
        compositeDisposable.add(
            permissionsInteractor.checkPermissions()
                .subscribe(
                    { permissionEvent.value = it },
                    { Log.e(it, "An error occurred while checking permissions") }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }

        SplashActivityComponentManager.clearSplashActivityComponent()
    }
}