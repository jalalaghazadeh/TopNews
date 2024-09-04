package com.mrjalal.topnews.domain.sync

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object NewsSyncScheduler {
    private const val REPEAT_INTERVAL = 5L
    private const val INITIAL_DELAY = 0L
    private const val NEWS_SYNC_WORKER_LABEL = "NewsSyncWork"

    /** Function to schedule the SyncNewsWorker to run every 5 minutes **/
    fun schedule(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<SyncNewsWorker>(REPEAT_INTERVAL, TimeUnit.MINUTES)
            .setInitialDelay(INITIAL_DELAY, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            NEWS_SYNC_WORKER_LABEL,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}