package com.mrjalal.topnews.domain.repository

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncNewsWorker @Inject constructor(
    @ApplicationContext context: Context,
    params: WorkerParameters,
    private val newsRepository: NewsRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        newsRepository.refreshNews()
        return Result.success()
    }

    companion object{
        private const val NEWS_SYNC_WORKER_LABEL = "NewsSyncWork"
        // Function to schedule the SyncNewsWorker to run every 5 minutes
        fun scheduleNewsSyncWorker(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<SyncNewsWorker>(5, TimeUnit.MINUTES)
                .setInitialDelay(0, TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                NEWS_SYNC_WORKER_LABEL,
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
        }
    }
}