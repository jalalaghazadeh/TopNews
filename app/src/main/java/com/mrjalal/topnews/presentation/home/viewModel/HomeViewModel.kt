package com.mrjalal.topnews.presentation.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mrjalal.topnews.domain.repository.category.CategoryRepository
import com.mrjalal.topnews.domain.repository.category.model.CategoryItemUiModel
import com.mrjalal.topnews.domain.repository.news.NewsRepository
import com.mrjalal.topnews.domain.repository.news.model.NewsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel(), HomeContract {
    private val _state = MutableStateFlow(HomeContract.State.EMPTY)
    override val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val effectChannel = Channel<HomeContract.Effect>()
    override val effect: Flow<HomeContract.Effect> = effectChannel.receiveAsFlow()

    val allNews: Flow<PagingData<NewsUiModel.NewsItemUiModel>> = _state
        .map { it.selectedCategory?.text ?: "" }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            newsRepository.getNewsByQuery(query).filterNotNull().cachedIn(viewModelScope)
        }
    val categories: Flow<List<CategoryItemUiModel>> = categoryRepository.fetchCategories()

    override fun event(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnToggleFilterSheet -> {
                _state.update { it.copy(showFilterSheet = event.show) }
            }

            is HomeContract.Event.OnCategoryItemClick -> {
                _state.update { it.copy(selectedCategory = event.item) }
            }
            is HomeContract.Event.OnDismissFilters -> {
                _state.update { it.copy(selectedCategory = null) }
            }
        }
    }
}