package com.mrjalal.topnews.data.dataSource.local.news

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrjalal.topnews.data.dataSource.local.news.entitiy.NewsItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_items ORDER BY publishedAt DESC LIMIT :pageSize OFFSET :page * :pageSize")
    fun getNews(page: Int, pageSize: Int): Flow<List<NewsItemEntity>>

    @Query("SELECT * FROM news_items ORDER BY publishedAt DESC")
    fun getNewsPagingSource(): PagingSource<Int, NewsItemEntity>

    @Query("SELECT * FROM news_items " +
            "WHERE title LIKE '%' || :queryName || '%' OR description LIKE '%' || :queryName || '%' OR content LIKE '%' || :queryName || '%' " +
            "ORDER BY publishedAt DESC")
    fun getNewsByQuery(queryName: String): PagingSource<Int, NewsItemEntity>

    @Query("SELECT * FROM news_items WHERE id = :id")
    fun getNewsById(id:String): Flow<NewsItemEntity>

    @Query("SELECT title FROM news_items")
    suspend fun getAllTitles(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<NewsItemEntity>)

    @Query("DELETE FROM news_items WHERE publishedAt < :expirationDate")
    suspend fun deleteOldArticles(expirationDate: Long)

//    @Query("SELECT * FROM category_items")
//    fun getCategories(): Flow<List<CategoryItemEntity>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCategories(companies: List<CategoryItemEntity>)
}