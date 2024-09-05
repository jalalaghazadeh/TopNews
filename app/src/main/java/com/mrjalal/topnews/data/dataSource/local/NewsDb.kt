package com.mrjalal.topnews.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrjalal.topnews.data.dataSource.local.category.CategoryDao
import com.mrjalal.topnews.data.dataSource.local.category.entity.CategoryItemEntity
import com.mrjalal.topnews.data.dataSource.local.news.NewsDao
import com.mrjalal.topnews.data.dataSource.local.news.entitiy.NewsItemEntity

@Database(entities = [NewsItemEntity::class, CategoryItemEntity::class], version = 1)
abstract class NewsDb: RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun categoryDao(): CategoryDao
}