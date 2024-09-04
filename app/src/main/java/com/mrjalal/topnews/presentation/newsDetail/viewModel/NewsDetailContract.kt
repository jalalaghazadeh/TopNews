package com.mrjalal.topnews.presentation.newsDetail.viewModel

import com.mrjalal.topnews.domain.repository.model.NewsUiModel
import com.mrjalal.topnews.presentation.common.helper.UnidirectionalViewModel

interface NewsDetailContract :
    UnidirectionalViewModel<NewsDetailContract.Event, NewsDetailContract.Effect, NewsDetailContract.State> {
    sealed interface Event {
        data class GetNewsById(val id: String): Event
    }
    sealed interface Effect
    data class State(
        val newsItem: NewsUiModel.NewsItemUiModel?
    ) {
        companion object {
            val EMPTY = State(
                newsItem = null
            )
        }
    }
}