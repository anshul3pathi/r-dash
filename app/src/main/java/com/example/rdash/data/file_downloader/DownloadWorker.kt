package com.example.rdash.data.file_downloader

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DownloadWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    private var lastUpdateTime = 0L

    companion object {
        private const val PROGRESS_UPDATE_INTERVAL_IN_MILLIS = 50
        private const val BUFFER_SIZE = 1024
        const val KEY_PROGRESS = "key_progress"
        const val KEY_URL = "key_url"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        lastUpdateTime = System.currentTimeMillis()
        val url = inputData.getString(KEY_URL) ?: return@withContext Result.failure()

        try {
            val urlObject = java.net.URL(url)
            val connection = urlObject.openConnection() as java.net.HttpURLConnection
            connection.connect()

            val fileLength = connection.contentLength

            val input: java.io.InputStream = java.io.BufferedInputStream(urlObject.openStream())
            val output = applicationContext.openFileOutput("downloaded_file", Context.MODE_PRIVATE)

            val data = ByteArray(BUFFER_SIZE)
            var total: Long = 0
            var count: Int

            while (input.read(data).also { count = it } != -1) {
                total += count.toLong()

                if (System.currentTimeMillis() - lastUpdateTime >= PROGRESS_UPDATE_INTERVAL_IN_MILLIS) {
                    val progress = (total * 100f / fileLength)
                    setProgressAsync(workDataOf(KEY_PROGRESS to progress))
                    lastUpdateTime = System.currentTimeMillis()
                }
            }

            output.flush()
            output.close()
            input.close()

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}