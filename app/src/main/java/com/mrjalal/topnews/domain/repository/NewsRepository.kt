package com.mrjalal.topnews.domain.repository

import com.mrjalal.topnews.data.dataSource.local.entitiy.NewsItemEntity
import com.mrjalal.topnews.data.dataSource.remote.model.NewsDto
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsByQuery(queryName: String): Flow<List<NewsItemEntity>>
    fun getNews(page: Int, pageSize: Int): Flow<List<NewsItemEntity>>
    suspend fun refreshNews(
//        query: String,
//        fromDate: String,
//        toDate: String,
//        sortBy: String,
//        page: String,
    )
}