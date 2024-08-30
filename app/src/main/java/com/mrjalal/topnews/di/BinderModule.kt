package com.mrjalal.topnews.di

import com.mrjalal.topnews.data.dataSource.remote.NewsRemoteDataSource
import com.mrjalal.topnews.data.dataSource.remote.NewsRemoteDataSourceImpl
import com.mrjalal.topnews.data.repository.NewsRepositoryImpl
import com.mrjalal.topnews.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {
    @Binds
    abstract fun bindRemoteDataSource(newsRemoteDataSourceImpl: NewsRemoteDataSourceImpl): NewsRemoteDataSource

    @Binds
    abstract fun bindRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}