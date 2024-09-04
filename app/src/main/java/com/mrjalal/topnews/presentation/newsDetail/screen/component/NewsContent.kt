package com.mrjalal.topnews.presentation.newsDetail.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mrjalal.topnews.R
import com.mrjalal.topnews.domain.repository.model.NewsUiModel
import com.mrjalal.topnews.presentation.common.component.TopNewsIcon

@Composable
fun NewsContent(news: NewsUiModel.NewsItemUiModel) {
    val contentShape = RoundedCornerShape(
        topStart = 32.dp,
        topEnd = 32.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(contentShape)
                .background(
                    color = Color.White,
                    shape = contentShape
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TopNewsIcon(
                    iconId = R.drawable.img_cnn,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = news.author ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
            }
            Text(
                text = news.content ?: "",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
