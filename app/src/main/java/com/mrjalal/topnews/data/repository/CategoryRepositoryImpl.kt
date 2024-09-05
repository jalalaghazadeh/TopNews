package com.mrjalal.topnews.data.repository

import android.util.Log
import com.mrjalal.topnews.data.dataSource.local.category.CategoryDao
import com.mrjalal.topnews.data.dataSource.remote.category.CategoryRemoteDataSource
import com.mrjalal.topnews.domain.repository.category.CategoryRepository
import com.mrjalal.topnews.domain.repository.category.model.CategoryItemUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: CategoryRemoteDataSource,
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun fetchCategories(): Flow<ImmutableList<CategoryItemUiModel>> {
        return categoryDao.getCategories().map { list -> list.map { it.toUiModel() }.toImmutableList() }
    }

    override suspend fun refreshCategories() {
        val response = remoteDataSource.fetchCategories()
        response.onSuccess {
            categoryDao.insertCategories(it.categories.map { category -> category.toEntity() })
        }.onFailure {
            Log.d(TAG, "Error fetching categories: ${it.message}")
        }
    }

    companion object{
        const val TAG = "oio"
    }
}