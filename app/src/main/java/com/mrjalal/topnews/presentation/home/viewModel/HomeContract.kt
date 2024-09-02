package com.mrjalal.topnews.presentation.home.viewModel

import com.mrjalal.topnews.presentation.common.helper.UnidirectionalViewModel

interface HomeContract:
    UnidirectionalViewModel<HomeContract.Event, HomeContract.Effect, HomeContract.State> {
    sealed interface Event
    sealed interface Effect
    data class State(
        val loading: Boolean
    ) {
        companion object {
            val EMPTY = State(
                loading = false
            )
        }
    }
}