package com.mrjalal.topnews.data.dataSource.local.category

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mrjalal.topnews.data.dataSource.local.NewsDb
import com.mrjalal.topnews.data.dataSource.local.category.CategoryDao
import com.mrjalal.topnews.data.dataSource.local.category.entity.CategoryItemEntity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {

    private lateinit var database: NewsDb // Replace with your actual database class
    private lateinit var categoryDao: CategoryDao

    @Before
    fun setup() {
        // Set up an in-memory Room database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDb::class.java
        ).allowMainThreadQueries().build() // Only use allowMainThreadQueries() for testing

        categoryDao = database.categoryDao()
    }

    @After
    fun teardown() {
        database.close() // Close the database after each test
    }

    @Test
    fun insertAndRetrieveCategories() = runTest {
        // Create sample category items
        val categoryItem1 = CategoryItemEntity(id = 1, text = "Apple", iconLink = "http://apple.com/icon.png")
        val categoryItem2 = CategoryItemEntity(id = 2, text = "Google", iconLink = "http://google.com/icon.png")

        // Insert the sample category items
        categoryDao.insertCategories(listOf(categoryItem1, categoryItem2))

        // Collect the categories using Flow
        val categories = categoryDao.getCategories().first() // Collects the first emission of the Flow

        // Verify the result
        assertEquals(2, categories.size)
        assertTrue(categories.contains(categoryItem1))
        assertTrue(categories.contains(categoryItem2))
    }

    @Test
    fun insertCategoriesWithConflict() = runTest {
        // Create initial category items
        val categoryItem1 = CategoryItemEntity(
            id = 1,
            text = "Apple",
            iconLink = "http://apple.com/icon.png"
        )
        categoryDao.insertCategories(listOf(categoryItem1))

        // Insert a new category item with the same ID but different data to test REPLACE strategy
        val updatedCategoryItem1 = CategoryItemEntity(
            id = 1,
            text = "Apple Updated",
            iconLink = "http://apple.com/updated_icon.png"
        )
        categoryDao.insertCategories(listOf(updatedCategoryItem1))

        // Collect the categories using Flow
        val categories = categoryDao.getCategories().first()

        // Verify that the category with the same ID was replaced
        assertEquals(1, categories.size)
        assertEquals(updatedCategoryItem1, categories.first())
    }
}
