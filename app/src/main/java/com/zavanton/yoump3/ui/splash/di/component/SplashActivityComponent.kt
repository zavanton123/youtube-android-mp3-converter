package com.zavanton.yoump3.ui.splash.di.component

import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.ui.splash.di.module.SplashActivityBindModule
import com.zavanton.yoump3.ui.splash.di.module.SplashActivityProvideModule
import com.zavanton.yoump3.ui.splash.view.SplashActivityViewModel
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        SplashActivityProvideModule::class,
        SplashActivityBindModule::class
    ]
)
interface SplashActivityComponent {

    fun inject(splashActivityViewModel: SplashActivityViewModel)
}