package com.mrjalal.topnews.di

import android.content.Context
import androidx.room.Room
import com.mrjalal.topnews.data.dataSource.local.news.NewsDao
import com.mrjalal.topnews.data.dataSource.local.NewsDb
import com.mrjalal.topnews.data.dataSource.local.category.CategoryDao
import com.mrjalal.topnews.data.dataSource.remote.news.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): NewsDb {
        return Room.databaseBuilder(
            context,
            NewsDb::class.java,
            "news_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(db: NewsDb): NewsDao {
        return db.newsDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(db: NewsDb): CategoryDao {
        return db.categoryDao()
    }
}