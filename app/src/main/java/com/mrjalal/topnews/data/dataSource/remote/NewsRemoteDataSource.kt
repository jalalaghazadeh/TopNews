package com.mrjalal.topnews.data.dataSource.remote

import com.mrjalal.topnews.data.dataSource.remote.model.NewsDto
import retrofit2.http.Query

interface NewsRemoteDataSource {
    suspend fun fetchNews(
        query: String,
        fromDate: String,
        toDate: String,
        sortBy: String,
        page: Int,
    ): Result<NewsDto>
}