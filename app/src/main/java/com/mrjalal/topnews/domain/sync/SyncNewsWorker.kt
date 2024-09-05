package com.mrjalal.topnews.domain.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mrjalal.topnews.domain.repository.category.CategoryRepository
import com.mrjalal.topnews.domain.repository.news.NewsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncNewsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val newsRepository: NewsRepository,
    private val categoryRepository: CategoryRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Log.d("oio", "doWork: SyncNewsWorker is started...")
        categoryRepository.refreshCategories()
        newsRepository.refreshNews()
        return Result.success()
    }
}