package com.zavanton.yoump3.ui.main.activity.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.ui.main.activity.di.component.MainActivityComponent
import com.zavanton.yoump3.ui.main.activity.di.module.MainActivityProvideModule
import com.zavanton.yoump3.utils.Logger
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MainActivityViewModel : ViewModel() {

    companion object {

        var mainActivityComponent: MainActivityComponent? by CustomDelegate()
    }

    init {
        Logger.d("MainActivityViewModel is init")
        mainActivityComponent?.inject(this)
    }

    override fun onCleared() {
        super.onCleared()

        Logger.d("MainActivityViewModel - onCleared")
        mainActivityComponent = null
    }

    private class CustomDelegate : ReadWriteProperty<Companion, MainActivityComponent?> {

        var component: MainActivityComponent? = null

        override fun getValue(
            thisRef: Companion,
            property: KProperty<*>
        ): MainActivityComponent? {

            Logger.d("CustomDelegate - getValue")
            component = TheApp.getAppComponent()
                .plusMainActivityComponent(MainActivityProvideModule())

            return component
        }

        override fun setValue(
            thisRef: Companion,
            property: KProperty<*>,
            value: MainActivityComponent?
        ) {
            Logger.d("CustomDelegate - setValue")
            component = value
        }
    }
}


