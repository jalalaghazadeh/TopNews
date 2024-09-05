package com.mrjalal.topnews.data.dataSource.remote.news.model

import com.mrjalal.topnews.data.dataSource.local.news.entitiy.NewsItemEntity
import com.mrjalal.topnews.domain.repository.util.Dto

data class NewsDto(
    val articles: List<NewsItemDto>,
    val status: String,
    val totalResults: Int
) {
    data class NewsItemDto(
        val author: String?,
        val content: String?,
        val description: String?,
        val publishedAt: String,
        val source: NewsSourceDto?,
        val title: String?,
        val url: String?,
        val urlToImage: String?
    ): Dto {
        override fun toEntity() = NewsItemEntity(
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            source = source?.name,
            title = title,
            url = url,
            urlToImage = urlToImage
        )
    }

    data class NewsSourceDto(
        val id: String?,
        val name: String
    )
}