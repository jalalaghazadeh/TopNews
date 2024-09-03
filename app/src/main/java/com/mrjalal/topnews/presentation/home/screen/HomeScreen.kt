package com.mrjalal.topnews.presentation.home.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.mrjalal.topnews.R
import com.mrjalal.topnews.domain.repository.model.NewsUiModel
import com.mrjalal.topnews.presentation.common.component.TopNewsStatusBar
import com.mrjalal.topnews.presentation.common.component.TopNewsTopBar
import com.mrjalal.topnews.presentation.common.helper.use
import com.mrjalal.topnews.presentation.home.screen.component.NewsItem
import com.mrjalal.topnews.presentation.home.viewModel.HomeViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.mrjalal.topnews.presentation.common.component.TopNewsCircleLoading
import com.mrjalal.topnews.presentation.common.component.TopNewsShimmerLoading

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    TopNewsStatusBar()

    val (state, effect, dispatcher) = use(viewModel)
    val allNews = viewModel.allNews.collectAsLazyPagingItems()

    HomeScreen(allNews)
}

@Composable
fun HomeScreen(allNews: LazyPagingItems<NewsUiModel.NewsItemUiModel>) {
    Scaffold(
        topBar = {
            TopNewsTopBar(
                title = stringResource(id = R.string.home_screen_title),
                leftIconId = R.drawable.ic_filter,
            )
        },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp)
        ) {
            items(
                count = allNews.itemCount,
                key = { "${allNews[it]?.id}" }
            ) { index ->
                NewsItem(allNews[index]!!)
                if (index < allNews.itemCount - 1) {
                    Spacer(modifier = Modifier.size(12.dp))
                }
            }

            allNews.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { TopNewsShimmerLoading() }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { TopNewsCircleLoading() }
                    }
                }
            }
        }
    }
}
