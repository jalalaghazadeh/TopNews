package com.mrjalal.topnews.data.dataSource.remote.category

import com.mrjalal.topnews.data.dataSource.remote.category.model.CategoryDto

interface CategoryRemoteDataSource {
    suspend fun fetchCategories(): Result<CategoryDto>
}