package com.mrjalal.topnews.data.dataSource.remote.news

import com.mrjalal.topnews.data.dataSource.remote.news.model.NewsDto
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val newsApi: NewsApi
): NewsRemoteDataSource {
    override suspend fun fetchNews(
        query: String,
        fromDate: String,
        toDate: String,
        sortBy: String,
        page: Int
    ): Result<NewsDto> {
        return runCatching { newsApi.fetchNews(query, fromDate, toDate, sortBy, page) }
    }
}