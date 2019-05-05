package com.zavanton.yoump3.ui.download.di.component

import com.zavanton.yoump3.di.scope.ServiceScope
import com.zavanton.yoump3.ui.download.di.module.DownloadServiceBindModule
import com.zavanton.yoump3.ui.download.di.module.DownloadServiceProvideModule
import com.zavanton.yoump3.ui.download.service.DownloadService
import dagger.Subcomponent

@ServiceScope
@Subcomponent(
    modules = [
        DownloadServiceProvideModule::class,
        DownloadServiceBindModule::class
    ]
)
interface DownloadServiceComponent {

    fun inject(downloadService: DownloadService)
}