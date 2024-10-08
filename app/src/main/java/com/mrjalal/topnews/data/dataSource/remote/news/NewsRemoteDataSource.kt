package com.mrjalal.topnews.data.dataSource.remote.news

import com.mrjalal.topnews.data.dataSource.remote.news.model.NewsDto

interface NewsRemoteDataSource {
    suspend fun fetchNews(
        query: String,
        fromDate: String,
        toDate: String,
        sortBy: String,
        page: Int,
    ): Result<NewsDto>
}