package com.mrjalal.topnews.domain.repository

import com.mrjalal.topnews.data.dataSource.local.entitiy.NewsItemEntity
import com.mrjalal.topnews.data.dataSource.remote.model.NewsDto
import com.mrjalal.topnews.domain.repository.model.NewsUiModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsByQuery(queryName: String): Flow<List<NewsUiModel.NewsItemUiModel>>
    fun getNews(page: Int, pageSize: Int): Flow<List<NewsUiModel.NewsItemUiModel>>
    suspend fun refreshNews()
}