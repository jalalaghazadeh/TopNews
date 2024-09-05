package com.mrjalal.topnews.data.repository

import com.mrjalal.topnews.data.dataSource.local.category.CategoryDao
import com.mrjalal.topnews.data.dataSource.local.category.entity.CategoryItemEntity
import com.mrjalal.topnews.data.dataSource.local.news.NewsDao
import com.mrjalal.topnews.data.dataSource.local.news.entitiy.NewsItemEntity
import com.mrjalal.topnews.data.dataSource.remote.news.NewsRemoteDataSource
import com.mrjalal.topnews.data.dataSource.remote.news.model.NewsDto
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryImplTest {

    // Mocked dependencies
    private lateinit var remoteDataSource: NewsRemoteDataSource
    private lateinit var newsDao: NewsDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var newsRepository: NewsRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteDataSource = mockk()
        newsDao = mockk()
        categoryDao = mockk()
        newsRepository = NewsRepositoryImpl(remoteDataSource, newsDao, categoryDao)
    }

    @Test
    fun `refreshNews should fetch and insert news items`() = runTest {
        // Mock the category items from CategoryDao
        val mockCategories = listOf(
            CategoryItemEntity(id = 1, text = "Apple", iconLink = ""),
            CategoryItemEntity(id = 2, text = "Google", iconLink = "")
        )

        // Mock the response from RemoteDataSource
        val mockNewsResponse = NewsDto(
            articles = listOf(
                NewsDto.NewsItemDto(
                    author = "Author",
                    content = "Content",
                    description = "Description",
                    publishedAt = "2024-09-01T12:00:00Z",
                    source = NewsDto.NewsSourceDto(id = "1", name = "Source"),
                    title = "News Title",
                    url = "https://example.com",
                    urlToImage = "https://example.com/image.png"
                )
            ),
            status = "ok",
            totalResults = 1
        )

        // Mocking behavior
        coEvery { categoryDao.getCategories() } returns flowOf(mockCategories)
        coEvery { remoteDataSource.fetchNews(any(), any(), any(), any(), any()) } returns Result.success(mockNewsResponse)
        coEvery { newsDao.getAllTitles() } returns emptyList()
        coEvery { newsDao.insertAll(any()) } just Runs

        // Call the method
        newsRepository.refreshNews()

        // Verify the methods were called
        coVerify { categoryDao.getCategories() }
        coVerify { remoteDataSource.fetchNews(any(), any(), any(), any(), any()) }
        coVerify { newsDao.insertAll(any()) }
        advanceUntilIdle()
    }

    @Test
    fun `getNewsById should return Flow of NewsItemUiModel`() = runTest {
        val newsEntity = NewsItemEntity(
            id = 1,
            title = "Apple News",
            description = "Description",
            content = "Content",
            publishedAt = "2024-09-04",
            author = "Author",
            source = "Source",
            url = "https://example.com",
            urlToImage = "https://example.com/image.png"
        )
        val newsFlow = flowOf(newsEntity)

        // Set up mock DAO to return the entity
        every { newsDao.getNewsById("1") } returns newsFlow

        val result = newsRepository.getNewsById("1").first()

        assertEquals("Apple News", result.title)
    }
}
