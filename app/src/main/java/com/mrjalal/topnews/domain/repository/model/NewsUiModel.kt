package com.mrjalal.topnews.domain.repository.model

import com.mrjalal.topnews.domain.repository.util.UiModel

data class NewsUiModel(
    val articles: List<NewsItemUiModel>,
    val status: String,
    val totalResults: Int
): UiModel {
    data class NewsItemUiModel(
        val author: String,
        val content: String,
        val description: String,
        val publishedAt: String,
        val source: String,
        val title: String,
        val url: String,
        val urlToImage: String
    ): UiModel
}