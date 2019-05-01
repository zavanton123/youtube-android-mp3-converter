package com.zavanton.yoump3.di.component

import com.zavanton.yoump3.di.module.AppModule
import com.zavanton.yoump3.ui.main.activity.di.MainActivityComponent
import com.zavanton.yoump3.ui.main.activity.di.MainActivityModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun plusMainActivityComponent(mainActivityModule: MainActivityModule): MainActivityComponent
}