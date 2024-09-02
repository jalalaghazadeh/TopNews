package com.mrjalal.topnews.presentation.home.viewModel

import androidx.lifecycle.ViewModel
import com.mrjalal.topnews.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    val allNews = newsRepository.getNews(page = 1, pageSize = 20) // todo: paging3

    override fun event(event: HomeContract.Event) {
        when(event) {
            else -> {}
        }
    }
}