package com.mrjalal.topnews.presentation.app

import android.app.Application
import com.mrjalal.topnews.domain.repository.SyncNewsWorker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TopNewsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        SyncNewsWorker.scheduleNewsSyncWorker(this)
    }
}