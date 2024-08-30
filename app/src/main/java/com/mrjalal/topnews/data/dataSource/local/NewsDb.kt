package com.mrjalal.topnews.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrjalal.topnews.data.dataSource.local.entitiy.NewsItemEntity

@Database(entities = [NewsItemEntity::class], version = 1)
abstract class NewsDb: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}