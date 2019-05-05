package com.zavanton.yoump3.ui.main.fragment.view

import androidx.lifecycle.ViewModel
import com.zavanton.yoump3.ui.main.activity.view.MainActivityViewModel
import com.zavanton.yoump3.ui.main.fragment.di.component.MainFragmentComponent
import com.zavanton.yoump3.ui.main.fragment.di.module.MainFragmentProvideModule
import com.zavanton.yoump3.ui.main.fragment.presenter.MainFragmentContract
import com.zavanton.yoump3.utils.Logger
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MainFragmentViewModel : ViewModel() {

    companion object {

        var mainFragmentComponent: MainFragmentComponent? by MainFragmentViewModelDelegate()
    }

    @Inject
    lateinit var presenter: MainFragmentContract.MvpPresenter

    init {
        Logger.d("MainFragmentViewModel is init")
        mainFragmentComponent?.inject(this)
    }

    override fun onCleared() {
        Logger.d("MainFragmentViewModel - onCleared")
        super.onCleared()

        mainFragmentComponent = null
    }

    class MainFragmentViewModelDelegate : ReadWriteProperty<Companion, MainFragmentComponent?> {

        private var component: MainFragmentComponent? = null

        override fun getValue(
            thisRef: Companion,
            property: KProperty<*>
        ): MainFragmentComponent? {

            Logger.d("MainFragmentViewModelDelegate - getValue")
            component = MainActivityViewModel.mainActivityComponent
                ?.plusMainFragmentComponent(MainFragmentProvideModule())

            return component
        }

        override fun setValue(
            thisRef: Companion,
            property: KProperty<*>, value: MainFragmentComponent?
        ) {
            Logger.d("MainFragmentViewModelDelegate - setValue")
            component = value
        }
    }
}