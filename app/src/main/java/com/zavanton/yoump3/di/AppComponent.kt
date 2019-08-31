package com.zavanton.yoump3.di

import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.di.module.*
import com.zavanton.yoump3.ui.download.di.component.DownloadServiceComponent
import com.zavanton.yoump3.ui.download.di.module.DownloadServiceProvideModule
import com.zavanton.yoump3.ui.main.activity.di.component.MainActivityComponent
import com.zavanton.yoump3.ui.main.activity.di.module.MainActivityProvideModule
import com.zavanton.yoump3.ui.splash.di.SplashActivityComponent
import com.zavanton.yoump3.ui.splash.di.SplashActivityProvideModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        SchedulerModule::class,
        ClipboardModule::class,
        NetworkModule::class,
        EventBusModule::class,
        NotificationModule::class,
        ConversionModule::class
    ]
)
interface AppComponent {

    fun inject(theApp: TheApp)

    fun plusSplashActivityComponent(module: SplashActivityProvideModule): SplashActivityComponent

    fun plusMainActivityComponent(module: MainActivityProvideModule): MainActivityComponent

    fun plusDownloadServiceComponent(module: DownloadServiceProvideModule): DownloadServiceComponent
}