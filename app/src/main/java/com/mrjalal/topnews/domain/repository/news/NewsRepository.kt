package com.mrjalal.topnews.domain.repository.news

import androidx.paging.PagingData
import com.mrjalal.topnews.domain.repository.news.model.NewsUiModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsByQuery(queryName: String): Flow<PagingData<NewsUiModel.NewsItemUiModel>>
    suspend fun getNewsById(id:String): Flow<NewsUiModel.NewsItemUiModel>
//    fun getNews(page: Int, pageSize: Int): Flow<List<NewsUiModel.NewsItemUiModel>>
//    fun getNews(): Flow<PagingData<NewsUiModel.NewsItemUiModel>>
    suspend fun refreshNews()
}