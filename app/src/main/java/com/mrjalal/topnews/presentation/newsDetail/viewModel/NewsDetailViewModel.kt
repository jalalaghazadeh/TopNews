package com.mrjalal.topnews.presentation.newsDetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrjalal.topnews.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(), NewsDetailContract {
    private val _state = MutableStateFlow(NewsDetailContract.State.EMPTY)
    override val state: StateFlow<NewsDetailContract.State> = _state.asStateFlow()

    private val effectChannel = Channel<NewsDetailContract.Effect>()
    override val effect: Flow<NewsDetailContract.Effect> = effectChannel.receiveAsFlow()

    override fun event(event: NewsDetailContract.Event) {
        when (event) {
            is NewsDetailContract.Event.GetNewsById -> {
                getNewsById(event.id)
            }
        }
    }

    private fun getNewsById(id: String) {
        viewModelScope.launch {
            newsRepository.getNewsById(id).collect { newsItem ->
                _state.update {
                    it.copy(
                        newsItem = newsItem
                    )
                }
            }
        }
    }
}