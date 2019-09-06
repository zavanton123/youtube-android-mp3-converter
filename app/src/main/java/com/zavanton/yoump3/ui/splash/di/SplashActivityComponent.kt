package com.zavanton.yoump3.ui.splash.di

import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.core.di.*
import com.zavanton.yoump3.ui.splash.viewModel.ISplashActivityViewModel
import com.zavanton.yoump3.ui.splash.viewModel.SplashActivityViewModel
import com.zavanton.yoump3.ui.splash.viewModel.SplashActivityViewModelFactory
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides

@ActivityScope
@Component(
    modules = [
        SplashActivityProvideModule::class,
        SplashActivityBindModule::class
    ],
    dependencies = [
        AppApi::class,
        SchedulerApi::class,
        ClipboardApi::class,
        NetworkApi::class,
        EventBusApi::class,
        NotificationApi::class,
        ConversionApi::class
    ]
)
interface SplashActivityComponent {

    fun inject(splashActivityViewModel: SplashActivityViewModelFactory)
}

@Module
class SplashActivityProvideModule(private val activity: FragmentActivity) {

    @ActivityScope
    @Provides
    fun provideActivity(): FragmentActivity = activity

    @ActivityScope
    @Provides
    fun provideRxPermissions(activity: FragmentActivity): RxPermissions = RxPermissions(activity)
}

@Module
interface SplashActivityBindModule {

    @Binds
    fun provideViewModel(impl: SplashActivityViewModel): ISplashActivityViewModel
}