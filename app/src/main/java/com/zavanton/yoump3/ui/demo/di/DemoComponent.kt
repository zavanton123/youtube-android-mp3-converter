package com.zavanton.yoump3.ui.demo.di

import com.zavanton.yoump3.di.scope.ActivityScope
import com.zavanton.yoump3.ui.demo.DemoViewModel
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        DemoModule::class
    ]
)
interface DemoComponent {

    fun inject(demoViewModel: DemoViewModel)
}