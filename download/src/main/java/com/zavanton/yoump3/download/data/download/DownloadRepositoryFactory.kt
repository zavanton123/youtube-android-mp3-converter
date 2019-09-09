package com.zavanton.yoump3.download.data.download

import android.content.Context
import com.zavanton.yoump3.core.di.ApplicationContext
import com.zavanton.yoump3.core.di.IoThreadScheduler
import com.zavanton.yoump3.core.di.ServiceScope
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import javax.inject.Inject

@ServiceScope
class DownloadRepositoryFactory @Inject constructor(
    @ApplicationContext
    private val appContext: Context,
    private val client: OkHttpClient,
    @IoThreadScheduler
    private val ioThreadScheduler: Scheduler
) : IDownloadRepositoryFactory {

    override fun newInstance(): IDownloadRepository =
        DownloadRepository(appContext, client, ioThreadScheduler)
}