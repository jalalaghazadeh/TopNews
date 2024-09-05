package com.mrjalal.topnews.presentation.home.viewModel

import com.mrjalal.topnews.domain.repository.category.model.CategoryItemUiModel
import com.mrjalal.topnews.presentation.common.helper.UnidirectionalViewModel

interface HomeContract:
    UnidirectionalViewModel<HomeContract.Event, HomeContract.Effect, HomeContract.State> {
    sealed interface Event{
        data class OnToggleFilterSheet(val show: Boolean) : Event
        data class OnCategoryItemClick(val item: CategoryItemUiModel) : Event
        data object OnDismissFilters: Event
    }
    sealed interface Effect
    data class State(
        val showFilterSheet: Boolean,
        val selectedCategory: CategoryItemUiModel?
    ) {
        companion object {
            val EMPTY = State(
                showFilterSheet = false,
                selectedCategory = null
            )
        }
    }
}