package com.mrjalal.topnews.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mrjalal.topnews.data.dataSource.local.NewsDao
import com.mrjalal.topnews.data.dataSource.remote.NewsRemoteDataSource
import com.mrjalal.topnews.domain.repository.NewsRepository
import com.mrjalal.topnews.domain.repository.model.NewsUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val newsDao: NewsDao
) : NewsRepository {
    override fun getNewsByQuery(queryName: String): Flow<List<NewsUiModel.NewsItemUiModel>> {
        return newsDao.getNewsByQuery(queryName).map { list -> list.map { it.toUiModel() } }
    }

    override fun getNews(page: Int, pageSize: Int): Flow<List<NewsUiModel.NewsItemUiModel>> {
        return newsDao.getNews(page, pageSize).map { list -> list.map { it.toUiModel() } }
    }

    override fun getNews(): Flow<PagingData<NewsUiModel.NewsItemUiModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = LOCAL_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { newsDao.getNewsPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toUiModel() }
        }
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
                    // Get titles of existing news articles from the database
                    val existingTitles = newsDao.getAllTitles()

                    // Filter out articles that are already in the database based on title
                    val newArticles = news.articles.filter { article ->
                        article.title !in existingTitles
                    }
                    // Insert the news articles into the database
                    newsDao.insertAll(newArticles.map { it.toEntity() })

                    // Check if there are more pages to fetch
                    hasMorePages = news.totalResults > currentPage*REMOTE_PAGE_SIZE
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
        const val REMOTE_PAGE_SIZE = 100 // default pageSize in NewsApi is 100
        const val LOCAL_PAGE_SIZE = 20 // pageSize for delivering data to UI
        const val TAG = "oio"
    }
}

// Function to get ISO 8601 formatted date string
fun getIsoFormattedDate(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return date.format(formatter)
}