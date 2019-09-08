package com.zavanton.yoump3.splash.di

import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.business.interactor.IPermissionInteractor
import com.zavanton.yoump3.business.interactor.PermissionInteractor
import com.zavanton.yoump3.core.di.*
import com.zavanton.yoump3.data.IPermissionService
import com.zavanton.yoump3.data.PermissionService
import com.zavanton.yoump3.download.di.EventBusApi
import com.zavanton.yoump3.splash.ui.viewModel.ISplashActivityViewModel
import com.zavanton.yoump3.splash.ui.viewModel.SplashActivityViewModel
import com.zavanton.yoump3.splash.ui.viewModel.SplashActivityViewModelFactory
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
        NotificationApi::class
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

    @Binds
    fun providePermissionInteractor(impl: PermissionInteractor): IPermissionInteractor

    @Binds
    fun providePermissionService(impl: PermissionService): IPermissionService
}