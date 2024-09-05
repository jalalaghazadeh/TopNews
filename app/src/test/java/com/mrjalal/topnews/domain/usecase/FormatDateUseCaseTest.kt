package com.mrjalal.topnews.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.mrjalal.topnews.domain.usecase.general.FormatDateUseCase
import org.junit.Test

class FormatDateUseCaseTest {

    private val formatDateUseCase = FormatDateUseCase()

    @Test
    fun `invoke should return formatted date when given valid ISO date string`() {
        // Arrange
        val isoDate = "2024-09-05T14:30:00Z"
        val expectedFormattedDate = "Sep 05, 2024"

        // Act
        val result = formatDateUseCase(isoDate)

        // Assert
        assertThat(result).isEqualTo(expectedFormattedDate)
    }

    @Test
    fun `invoke should return the original string when given invalid date format`() {
        // Arrange
        val invalidDate = "invalid-date-format"

        // Act
        val result = formatDateUseCase(invalidDate)

        // Assert
        assertThat(result).isEqualTo(invalidDate)
    }
}
