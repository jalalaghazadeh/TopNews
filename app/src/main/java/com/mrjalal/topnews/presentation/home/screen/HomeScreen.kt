package com.mrjalal.topnews.presentation.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mrjalal.topnews.R
import com.mrjalal.topnews.domain.repository.model.NewsUiModel
import com.mrjalal.topnews.presentation.app.ui.theme.Gray_7
import com.mrjalal.topnews.presentation.common.component.TopNewsStatusBar
import com.mrjalal.topnews.presentation.common.component.TopNewsTopBar
import com.mrjalal.topnews.presentation.common.helper.use
import com.mrjalal.topnews.presentation.home.screen.component.NewsItem
import com.mrjalal.topnews.presentation.home.viewModel.HomeViewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    TopNewsStatusBar()

    val (state, effect, dispatcher) = use(viewModel)
    val allNews by viewModel.allNews.collectAsStateWithLifecycle(listOf())

    HomeScreen(allNews)
}

@Composable
fun HomeScreen(
    allNews: List<NewsUiModel.NewsItemUiModel>
) {
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
            items(count = allNews.size, key = { "${allNews[it].id}" }) {
                NewsItem(allNews[it])
                if (it < allNews.lastIndex) {
                    Spacer(modifier = Modifier.size(12.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPrev() {
    HomeScreen(
        allNews = NewsUiModel.PREVIEW.articles
    )
}
