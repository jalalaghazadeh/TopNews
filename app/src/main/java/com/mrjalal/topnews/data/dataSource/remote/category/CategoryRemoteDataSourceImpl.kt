package com.mrjalal.topnews.data.dataSource.remote.category

import com.mrjalal.topnews.data.dataSource.remote.category.model.CategoryDto
import com.mrjalal.topnews.data.dataSource.remote.news.model.NewsDto
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(): CategoryRemoteDataSource {
    override suspend fun fetchCategories(): Result<CategoryDto> {
        return runCatching { CategoryDto.MOCK }
    }
}