package com.mrjalal.topnews.di

import com.mrjalal.topnews.data.dataSource.remote.category.CategoryRemoteDataSource
import com.mrjalal.topnews.data.dataSource.remote.category.CategoryRemoteDataSourceImpl
import com.mrjalal.topnews.data.dataSource.remote.news.NewsRemoteDataSource
import com.mrjalal.topnews.data.dataSource.remote.news.NewsRemoteDataSourceImpl
import com.mrjalal.topnews.data.repository.CategoryRepositoryImpl
import com.mrjalal.topnews.data.repository.NewsRepositoryImpl
import com.mrjalal.topnews.domain.repository.category.CategoryRepository
import com.mrjalal.topnews.domain.repository.news.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {
    @Binds
    abstract fun bindNewsRemoteDataSource(newsRemoteDataSourceImpl: NewsRemoteDataSourceImpl): NewsRemoteDataSource

    @Binds
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

    @Binds
    abstract fun bindCategoryRemoteDataSource(categoryRemoteDataSourceImpl: CategoryRemoteDataSourceImpl): CategoryRemoteDataSource

    @Binds
    abstract fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository
}