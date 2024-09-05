package com.mrjalal.topnews.data.dataSource.local.news

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mrjalal.topnews.data.dataSource.local.NewsDb
import com.mrjalal.topnews.data.dataSource.local.news.NewsDao
import com.mrjalal.topnews.data.dataSource.local.news.entitiy.NewsItemEntity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NewsDaoTest {

    private lateinit var database: NewsDb // Your Room database class
    private lateinit var newsDao: NewsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDb::class.java
        ).allowMainThreadQueries().build()

        newsDao = database.newsDao()
    }

    @After
    fun teardown() {
        database.close() // Close database after tests
    }

    @Test
    fun insertAndRetrieveNews() = runTest {
        // Sample data to insert
        val newsItem = NewsItemEntity(
            id = 1,
            title = "Test News",
            description = "Test Description",
            content = "Test Content",
            publishedAt = "2024-09-01T12:00:00",
            source = "Test Source",
            url = "http://test.url",
            urlToImage = "http://test.image.url",
            author = "Shank"
        )

        // Insert the news item
        newsDao.insertAll(listOf(newsItem))

        // Retrieve the news item by title query
        val pagingSource = newsDao.getNewsByQuery("Test")
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Verify the result
        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(1, page.data.size)
        assertEquals(newsItem.title, page.data.first().title)
    }

    @Test
    fun getAllTitles() = runTest {
        val newsItem1 = NewsItemEntity(
            id = 1,
            title = "Title 1",
            description = "Test Description",
            content = "Test Content",
            publishedAt = "2024-09-01T12:00:00",
            source = "Test Source",
            url = "http://test.url",
            urlToImage = "http://test.image.url",
            author = "Shank"
        )
        val newsItem2 = NewsItemEntity(
            id = 2,
            title = "Title 2",
            description = "Test Description",
            content = "Test Content",
            publishedAt = "2024-09-01T12:00:00",
            source = "Test Source",
            url = "http://test.url",
            urlToImage = "http://test.image.url",
            author = "Shank"
        )

        newsDao.insertAll(listOf(newsItem1, newsItem2))

        val titles = newsDao.getAllTitles()

        assertEquals(2, titles.size)
        assertTrue(titles.contains("Title 1"))
        assertTrue(titles.contains("Title 2"))
    }
}
