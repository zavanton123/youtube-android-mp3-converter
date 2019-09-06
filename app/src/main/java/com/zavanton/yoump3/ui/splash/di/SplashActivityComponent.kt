package com.zavanton.yoump3.ui.splash.di

import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zavanton.yoump3.core.di.ActivityScope
import com.zavanton.yoump3.ui.splash.viewModel.ISplashActivityViewModel
import com.zavanton.yoump3.ui.splash.viewModel.SplashActivityViewModel
import com.zavanton.yoump3.ui.splash.viewModel.SplashActivityViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        SplashActivityProvideModule::class,
        SplashActivityBindModule::class
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