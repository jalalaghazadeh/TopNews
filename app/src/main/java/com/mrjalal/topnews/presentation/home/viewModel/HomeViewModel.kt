package com.mrjalal.topnews.presentation.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mrjalal.topnews.domain.repository.NewsRepository
import com.mrjalal.topnews.domain.repository.model.NewsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel(), HomeContract {
    private val _state = MutableStateFlow(HomeContract.State.EMPTY)
    override val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val effectChannel = Channel<HomeContract.Effect>()
    override val effect: Flow<HomeContract.Effect> = effectChannel.receiveAsFlow()

    val allNews: Flow<PagingData<NewsUiModel.NewsItemUiModel>> = newsRepository.getNews().filterNotNull().cachedIn(viewModelScope)

    override fun event(event: HomeContract.Event) {
        when(event) {
            else -> {}
        }
    }
}