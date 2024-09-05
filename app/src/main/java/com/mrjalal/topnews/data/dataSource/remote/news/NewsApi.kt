package com.mrjalal.topnews.data.dataSource.remote.news

import com.mrjalal.topnews.data.dataSource.remote.news.model.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("v2/everything")
    suspend fun fetchNews(
        @Query("q") query: String,
        @Query("from") fromDate: String,
        @Query("to") toDate: String,
        @Query("sortBy") sortBy: String,
        @Query("page") page: Int,
    ): NewsDto
}