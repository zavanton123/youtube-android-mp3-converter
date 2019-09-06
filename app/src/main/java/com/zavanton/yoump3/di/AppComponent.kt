package com.zavanton.yoump3.di

import com.zavanton.yoump3.app.TheApp
import com.zavanton.yoump3.core.di.AppScope
import dagger.Component


@AppScope
@Component
interface AppComponent {

    fun inject(theApp: TheApp)
}