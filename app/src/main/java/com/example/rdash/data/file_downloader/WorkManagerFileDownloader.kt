package com.example.rdash.data.file_downloader

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WorkManagerFileDownloader(
    private val context: Context,
) : FileDownloader {

    override fun download(fileUrl: String): Flow<Float?> {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val downloadWorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .setExpedited(OutOfQuotaPolicy.DROP_WORK_REQUEST)
            .setInputData(Data.Builder().putString(DownloadWorker.KEY_URL, fileUrl).build())
            .build()

        WorkManager.getInstance(context).enqueue(downloadWorkRequest)

        return WorkManager.getInstance(context)
            .getWorkInfoByIdFlow(downloadWorkRequest.id)
            .map { workInfo ->
                workInfo?.let {
                    when (it.state) {
                        WorkInfo.State.RUNNING -> {
                            it.progress.getFloat(DownloadWorker.KEY_PROGRESS, 0f)
                        }

                        WorkInfo.State.SUCCEEDED -> 100f
                        WorkInfo.State.FAILED -> null
                        else -> null
                    }
                }
            }
    }
}