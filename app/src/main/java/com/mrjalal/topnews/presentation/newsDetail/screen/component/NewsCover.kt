package com.mrjalal.topnews.presentation.newsDetail.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrjalal.topnews.domain.repository.news.model.NewsUiModel
import com.mrjalal.topnews.presentation.app.ui.theme.Blue_1
import com.mrjalal.topnews.presentation.common.component.TopNewsRemoteImage
import com.mrjalal.topnews.presentation.common.helper.screenHeightDp

@Composable
fun NewsCover(news: NewsUiModel.NewsItemUiModel) {
    val coverImageHeight = (screenHeightDp() / 2).dp
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        news.urlToImage?.let {
            TopNewsRemoteImage(
                url = it,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = coverImageHeight)
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(
                    brush = Brush.verticalGradient(
                        colors = SMOOTH_BLACK_GRADIENT
                    )
                )
                .padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .background(
                        color = Blue_1,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = news.source ?: "",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        lineHeight = 20.sp
                    )
                )
            }
            Text(
                text = news.title ?: "",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                ),
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
            Text(
                text = news.publishedAt,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}

val SMOOTH_BLACK_GRADIENT = listOf(
    Color.Transparent,
    Color.Black.copy(alpha = 0.1f),
    Color.Black.copy(alpha = 0.2f),
    Color.Black.copy(alpha = 0.3f),
    Color.Black.copy(alpha = 0.4f),
    Color.Black.copy(alpha = 0.5f),
    Color.Black.copy(alpha = 0.6f),
    Color.Black.copy(alpha = 0.7f),
    Color.Black.copy(alpha = 0.8f),
    Color.Black.copy(alpha = 0.9f),
    Color.Black,
)