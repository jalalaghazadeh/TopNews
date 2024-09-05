package com.mrjalal.topnews.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mrjalal.topnews.data.dataSource.local.category.CategoryDao
import com.mrjalal.topnews.data.dataSource.local.news.NewsDao
import com.mrjalal.topnews.data.dataSource.remote.news.NewsRemoteDataSource
import com.mrjalal.topnews.domain.repository.news.NewsRepository
import com.mrjalal.topnews.domain.repository.news.model.NewsUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val newsDao: NewsDao,
    private val categoryDao: CategoryDao,
) : NewsRepository {
    override fun getNewsByQuery(queryName: String): Flow<PagingData<NewsUiModel.NewsItemUiModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = LOCAL_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { newsDao.getNewsByQuery(queryName) }
        ).flow.map { pagingData ->
            pagingData.map { it.toUiModel() }
        }
    }

    override suspend fun getNewsById(id: String): Flow<NewsUiModel.NewsItemUiModel> {
        return newsDao.getNewsById(id).map { it.toUiModel() }
    }

    override suspend fun refreshNews() {
        // Define default date ranges for the API query
        val defaultFromDate: String = getIsoFormattedDate(
            LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0)
        )
        val defaultToDate: String = getIsoFormattedDate(LocalDateTime.now())
        val defaultSortedBy = "publishedAt"

        // Collect company names from the database
        categoryDao.getCategories().collect { companies ->
            for (company in companies) {
                var currentPage = 1
                var hasMorePages = true

                while (hasMorePages) {
                    // Fetch news for the current company and page
                    val response = remoteDataSource.fetchNews(
                        query = company.text,
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

                        // Insert the new news articles into the database
                        newsDao.insertAll(newArticles.map { it.toEntity() })

                        // Check if there are more pages to fetch
                        hasMorePages = news.totalResults > currentPage * REMOTE_PAGE_SIZE
                        currentPage++
                    }.onFailure {
                        // Stop fetching pages if there's an error
                        hasMorePages = false
                        Log.d(TAG, "Error fetching news for $company: ${it.message}")
                    }

                    // Delay to prevent rate-limit errors
                    delay(1000) // Adjust if needed based on rate-limit guidelines
                }
            }
        }
    }


    companion object{
        const val REMOTE_PAGE_SIZE = 100 // default pageSize in NewsApi is 100
        const val LOCAL_PAGE_SIZE = 6 // pageSize for delivering data to UI
        const val TAG = "oio"
    }
}

fun getIsoFormattedDate(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return date.format(formatter)
}