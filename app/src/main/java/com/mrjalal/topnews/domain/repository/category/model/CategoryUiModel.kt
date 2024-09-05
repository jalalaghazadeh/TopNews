package com.mrjalal.topnews.domain.repository.category.model

import com.mrjalal.topnews.domain.repository.util.UiModel

data class CategoryItemUiModel(
    val id: Int,
    val text: String,
    val iconLink: String,
) : UiModel

