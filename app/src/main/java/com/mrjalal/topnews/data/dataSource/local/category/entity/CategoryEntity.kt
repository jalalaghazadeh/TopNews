package com.mrjalal.topnews.data.dataSource.local.category.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrjalal.topnews.domain.repository.category.model.CategoryItemUiModel
import com.mrjalal.topnews.domain.repository.util.NewsEntity

@Entity(tableName = "category_items")
data class CategoryItemEntity(
    @PrimaryKey val id: Int,
    val text: String,
    val iconLink: String,
) : NewsEntity {
    override fun toUiModel() = CategoryItemUiModel(
        id, text, iconLink
    )
}
