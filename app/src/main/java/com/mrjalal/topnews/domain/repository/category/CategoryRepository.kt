package com.mrjalal.topnews.domain.repository.category

import androidx.paging.PagingData
import com.mrjalal.topnews.domain.repository.category.model.CategoryItemUiModel
import com.mrjalal.topnews.domain.repository.news.model.NewsUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun fetchCategories(): Flow<ImmutableList<CategoryItemUiModel>>
    suspend fun refreshCategories()
}