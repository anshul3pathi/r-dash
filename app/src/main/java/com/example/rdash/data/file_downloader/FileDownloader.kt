package com.example.rdash.data.file_downloader

import kotlinx.coroutines.flow.Flow


interface FileDownloader {
    fun download(fileUrl: String): Flow<Float?>
}