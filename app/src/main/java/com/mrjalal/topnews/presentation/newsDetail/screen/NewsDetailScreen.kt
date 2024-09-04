package com.mrjalal.topnews.presentation.newsDetail.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrjalal.topnews.R
import com.mrjalal.topnews.domain.repository.model.NewsUiModel
import com.mrjalal.topnews.presentation.app.ui.theme.Blue_1
import com.mrjalal.topnews.presentation.app.ui.theme.Gray
import com.mrjalal.topnews.presentation.app.ui.theme.Gray4
import com.mrjalal.topnews.presentation.app.ui.theme.Gray_6
import com.mrjalal.topnews.presentation.common.component.TopNewsIcon
import com.mrjalal.topnews.presentation.common.component.TopNewsRemoteImage
import com.mrjalal.topnews.presentation.common.component.TopNewsShimmerLoading
import com.mrjalal.topnews.presentation.common.component.TopNewsStatusBar
import com.mrjalal.topnews.presentation.common.component.TopNewsTopBar
import com.mrjalal.topnews.presentation.common.helper.screenHeightDp
import com.mrjalal.topnews.presentation.common.helper.use
import com.mrjalal.topnews.presentation.newsDetail.screen.component.NewsContent
import com.mrjalal.topnews.presentation.newsDetail.screen.component.NewsCover
import com.mrjalal.topnews.presentation.newsDetail.viewModel.NewsDetailContract
import com.mrjalal.topnews.presentation.newsDetail.viewModel.NewsDetailViewModel

@Composable
fun NewsDetailRoute(
    viewModel: NewsDetailViewModel = hiltViewModel(),
    newsId: String?,
    onBack: () -> Unit
) {
    TopNewsStatusBar(Color.Black.copy(alpha = 0.3f), isLight = false)

    val (state, effect, dispatcher) = use(viewModel)

    LaunchedEffect(newsId) {
        newsId?.let {
            dispatcher(NewsDetailContract.Event.GetNewsById(it))
        }
    }

    val scrollState = rememberScrollState()
    NewsDetailScreen(
        news = state.newsItem,
        scrollState = scrollState,
        onBack = onBack
    )
}

@Composable
fun NewsDetailScreen(
    news: NewsUiModel.NewsItemUiModel?,
    scrollState: ScrollState,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopNewsTopBar(
            title = "",
            rightIconId = R.drawable.ic_left_arrow,
            onRightIconClick = onBack,
            backgroundColor = Color.Transparent,
            modifier = Modifier.zIndex(10f).statusBarsPadding().align(Alignment.TopCenter).padding(top = 0.dp),
            iconTintColor = Color.White,
            iconBackgroundColor = Color.Black.copy(alpha = 0.3f)
        )
        if (news == null) {
            TopNewsShimmerLoading()
        } else {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                NewsCover(news)
                NewsContent(news)
            }
        }
    }
}