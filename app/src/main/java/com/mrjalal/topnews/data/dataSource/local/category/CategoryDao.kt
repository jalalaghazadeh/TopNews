package com.mrjalal.topnews.data.dataSource.local.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrjalal.topnews.data.dataSource.local.category.entity.CategoryItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category_items")
    fun getCategories(): Flow<List<CategoryItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(companies: List<CategoryItemEntity>)
}