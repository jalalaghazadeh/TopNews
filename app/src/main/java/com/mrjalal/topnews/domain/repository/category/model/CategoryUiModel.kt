package com.mrjalal.topnews.domain.repository.category.model

import androidx.compose.runtime.Stable
import com.mrjalal.topnews.domain.repository.util.UiModel

@Stable
data class CategoryItemUiModel(
    val id: Int,
    val text: String,
    val iconLink: String,
) : UiModel

