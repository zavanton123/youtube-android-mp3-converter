package com.zavanton.yoump3.download.data.download

interface IDownloadRepositoryFactory {

    fun newInstance(): IDownloadRepository
}