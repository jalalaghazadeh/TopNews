package com.mrjalal.topnews.data.repository

import android.util.Log
import com.mrjalal.topnews.data.dataSource.local.NewsDao
import com.mrjalal.topnews.data.dataSource.local.entitiy.NewsItemEntity
import com.mrjalal.topnews.data.dataSource.remote.NewsRemoteDataSource
import com.mrjalal.topnews.data.dataSource.remote.model.NewsDto
import com.mrjalal.topnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val newsDao: NewsDao
) : NewsRepository {
    override fun getNewsByQuery(queryName: String): Flow<List<NewsItemEntity>> {
        return newsDao.getNewsByQuery(queryName)
    }

    override fun getNews(page: Int, pageSize: Int): Flow<List<NewsItemEntity>> {
        return newsDao.getNews(page, pageSize)
    }

    override suspend fun refreshNews() {
        val defaultFromDate: String = getIsoFormattedDate(
            LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0)
        )
        val defaultToDate: String = getIsoFormattedDate(LocalDateTime.now())
        val defaultSortedBy = "publishedAt"

        // todo: find a better way to fetch business's names other than hard-coding
        val companies = listOf("Microsoft", "Apple", "Google", "Tesla")

        // Iterate through each company and fetch news
        for (company in companies) {
            var currentPage = 1
            var hasMorePages = true

            while (hasMorePages) {
                // Fetch news for the current company and page
                val response = remoteDataSource.fetchNews(
                    query = company,
                    fromDate = defaultFromDate,
                    toDate = defaultToDate,
                    sortBy = defaultSortedBy,
                    page = currentPage
                )

                // Handle the API response
                response.onSuccess { news ->
                    // Insert the news articles into the database
                    newsDao.insertAll(news.articles.map { it.toEntity() })

                    // Check if there are more pages to fetch
                    hasMorePages = news.totalResults > currentPage*PAGE_SIZE
                    currentPage++
                }.onFailure {
                    // Stop fetching pages if there's an error
                    hasMorePages = false
                    Log.d(TAG, "Error fetching news for $company: ${it.message}")
                }
            }
        }
    }

    companion object{
        const val PAGE_SIZE = 100 // default pageSize in NewsApi is 100
        const val TAG = "oio"
    }
}

// Function to get ISO 8601 formatted date string
fun getIsoFormattedDate(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return date.format(formatter)
}