package com.zavanton.yoump3.di.component

import com.zavanton.yoump3.di.module.AppModule
import com.zavanton.yoump3.ui.main.activity.di.component.MainActivityComponent
import com.zavanton.yoump3.ui.main.activity.di.module.MainActivityProvideModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun plusMainActivityComponent(mainActivityProvideModule: MainActivityProvideModule): MainActivityComponent
}