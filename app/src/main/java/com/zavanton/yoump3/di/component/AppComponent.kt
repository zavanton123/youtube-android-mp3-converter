package com.zavanton.yoump3.di.component

import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.di.module.AppModule
import com.zavanton.yoump3.ui.main.activity.di.component.MainActivityComponent
import com.zavanton.yoump3.ui.main.activity.di.module.MainActivityProvideModule
import com.zavanton.yoump3.ui.download.di.component.DownloadServiceComponent
import com.zavanton.yoump3.ui.download.di.module.DownloadServiceProvideModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun inject(theApp: TheApp)

    fun plusMainActivityComponent(mainActivityProvideModule: MainActivityProvideModule): MainActivityComponent

    fun plusDownloadServiceComponent(downloadServiceProvideModule: DownloadServiceProvideModule): DownloadServiceComponent
}