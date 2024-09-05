package com.mrjalal.topnews.data.repository

import com.google.common.truth.Truth.assertThat
import com.mrjalal.topnews.data.dataSource.local.category.CategoryDao
import com.mrjalal.topnews.data.dataSource.local.category.entity.CategoryItemEntity
import com.mrjalal.topnews.data.dataSource.remote.category.CategoryRemoteDataSource
import com.mrjalal.topnews.data.dataSource.remote.category.model.CategoryDto
import io.mockk.*
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CategoryRepositoryImplTest {

    private lateinit var categoryRepository: CategoryRepositoryImpl
    private val remoteDataSource: CategoryRemoteDataSource = mockk()
    private val categoryDao: CategoryDao = mockk()

    @Before
    fun setUp() {
        categoryRepository = CategoryRepositoryImpl(remoteDataSource, categoryDao)
    }

    @Test
    fun `fetchCategories should return categories from DAO`() = runTest {
        // Arrange
        val categoryEntities = listOf(
            CategoryItemEntity(1, "Category 1", "https://link1.com"),
            CategoryItemEntity(2, "Category 2", "https://link2.com")
        )
        every { categoryDao.getCategories() } returns flowOf(categoryEntities)

        // Act
        val result = categoryRepository.fetchCategories().first()

        // Assert
        assertThat(result).isEqualTo(categoryEntities.map { it.toUiModel() }.toImmutableList())
        verify { categoryDao.getCategories() }
        confirmVerified(categoryDao)
    }

    @Test
    fun `refreshCategories should insert categories from remote data source`() = runTest {
        // Arrange
        val categoryDtos = listOf(
            CategoryDto.CategoryItemDto(1, "Category 1", "https://link1.com"),
            CategoryDto.CategoryItemDto(2, "Category 2", "https://link2.com")
        )
        val categoryResponse = Result.success(CategoryDto(categoryDtos))
        coEvery { remoteDataSource.fetchCategories() } returns categoryResponse
        coEvery { categoryDao.insertCategories(any()) } just Runs

        // Act
        categoryRepository.refreshCategories()

        // Assert
        coVerify { categoryDao.insertCategories(categoryDtos.map { it.toEntity() }) }
        coVerify { remoteDataSource.fetchCategories() }
        confirmVerified(remoteDataSource, categoryDao)
    }
}