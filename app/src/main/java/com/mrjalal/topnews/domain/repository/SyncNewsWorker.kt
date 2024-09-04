package com.mrjalal.topnews.domain.repository

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncNewsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val newsRepository: NewsRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Log.d("oio", "doWork: SyncNewsWorker is started...")
        newsRepository.refreshNews()
        return Result.success()
    }
}