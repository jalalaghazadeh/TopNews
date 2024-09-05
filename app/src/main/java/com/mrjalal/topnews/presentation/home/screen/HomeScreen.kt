package com.mrjalal.topnews.presentation.home.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mrjalal.topnews.R
import com.mrjalal.topnews.domain.repository.category.model.CategoryItemUiModel
import com.mrjalal.topnews.domain.repository.news.model.NewsUiModel
import com.mrjalal.topnews.presentation.app.ui.theme.Blue_1
import com.mrjalal.topnews.presentation.app.ui.theme.Gray_12
import com.mrjalal.topnews.presentation.app.ui.theme.Gray_7
import com.mrjalal.topnews.presentation.common.component.TopNewsBottomSheet
import com.mrjalal.topnews.presentation.common.component.TopNewsCircleLoading
import com.mrjalal.topnews.presentation.common.component.TopNewsRemoteImage
import com.mrjalal.topnews.presentation.common.component.TopNewsShimmerLoading
import com.mrjalal.topnews.presentation.common.component.TopNewsStatusBar
import com.mrjalal.topnews.presentation.common.component.TopNewsTopBar
import com.mrjalal.topnews.presentation.common.helper.noRippleClickable
import com.mrjalal.topnews.presentation.common.helper.use
import com.mrjalal.topnews.presentation.home.screen.component.NewsItem
import com.mrjalal.topnews.presentation.home.viewModel.HomeContract
import com.mrjalal.topnews.presentation.home.viewModel.HomeViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToNewsDetail: (String) -> Unit
) {
    TopNewsStatusBar()

    val (state, _, dispatcher) = use(viewModel)
    val allNews = viewModel.allNews.collectAsLazyPagingItems()
    val categories by viewModel.categories.collectAsStateWithLifecycle(initialValue = emptyList())

    val scope = rememberCoroutineScope()
    val filterSheetState = rememberModalBottomSheetState()
    val onToggleFilterSheet: (Boolean) -> Unit = { show ->
        scope.launch {
            if (state.showFilterSheet) {
                filterSheetState.hide()
            } else {
                filterSheetState.show()
            }
        }.invokeOnCompletion {
            dispatcher(HomeContract.Event.OnToggleFilterSheet(show))
        }
    }

    Box(modifier = Modifier.safeDrawingPadding()) {
        HomeScreen(
            allNews = allNews,
            showFilterSheet = state.showFilterSheet,
            categories = categories.toImmutableList(),
            selectedCategory = state.selectedCategory,
            sheetState = filterSheetState,
            onNavigateToNewsDetail = onNavigateToNewsDetail,
            onToggleFilterSheet = onToggleFilterSheet,
            onCategoryItemClick = {
                dispatcher(HomeContract.Event.OnCategoryItemClick(it))
                onToggleFilterSheet(false)
            },
            onDismissFilters = {
                dispatcher(HomeContract.Event.OnDismissFilters)
            }
        )
    }
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun HomeScreen(
    allNews: LazyPagingItems<NewsUiModel.NewsItemUiModel>,
    showFilterSheet: Boolean,
    categories: ImmutableList<CategoryItemUiModel>,
    selectedCategory: CategoryItemUiModel?,
    sheetState: SheetState,
    onNavigateToNewsDetail: (String) -> Unit,
    onToggleFilterSheet: (Boolean) -> Unit,
    onCategoryItemClick: (CategoryItemUiModel) -> Unit,
    onDismissFilters: () -> Unit
) {
    Scaffold(
        topBar = {
            TopNewsTopBar(
                title = stringResource(id = R.string.home_screen_title),
                leftIconId = R.drawable.ic_filter,
                onLeftIconClick = {
                    onToggleFilterSheet(true)
                }
            )
        },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
            ,
            contentPadding = PaddingValues(start = 20.dp, top = 20.dp, end = 20.dp)
        ) {
            stickyHeader {
                selectedCategory?.let {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    ) {
                        CategoryTag(
                            item = it,
                            onDismiss = onDismissFilters
                        )
                    }
                }
            }
            items(
                count = allNews.itemCount,
                key = { "${allNews[it]?.id}" }
            ) { index ->
                NewsItem(
                    item = allNews[index]!!,
                    onItemClick = onNavigateToNewsDetail
                )
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

        if (showFilterSheet) {
            TopNewsBottomSheet(
                sheetState = sheetState,
                title = stringResource(id = R.string.home_screen_filter_sheet_title),
                onDismiss = { onToggleFilterSheet(false) },
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(categories.size) { index ->
                        FilterItem(
                            item = categories[index],
                            onClick = {
                                onCategoryItemClick(categories[index])
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryTag(
    item: CategoryItemUiModel,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(36.dp)
            .background(
                color = Blue_1,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .noRippleClickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = item.text,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    lineHeight = 20.sp
                )
            )
        }
    }
}

@Composable
private fun FilterItem(
    item: CategoryItemUiModel,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray_12,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .noRippleClickable { onClick() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopNewsRemoteImage(
                url = item.iconLink, modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(color = Gray_7, shape = CircleShape)
            )

            Text(
                text = item.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
