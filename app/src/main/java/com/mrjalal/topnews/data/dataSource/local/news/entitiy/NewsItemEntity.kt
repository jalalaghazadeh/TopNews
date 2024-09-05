package com.mrjalal.topnews.data.dataSource.local.news.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrjalal.topnews.domain.repository.news.model.NewsUiModel
import com.mrjalal.topnews.domain.repository.util.NewsEntity

@Entity(tableName = "news_items")
data class NewsItemEntity (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
): NewsEntity {
    override fun toUiModel() = NewsUiModel.NewsItemUiModel(
        id, author, content, description, publishedAt, source, title, url, urlToImage
    )
}