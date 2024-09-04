package com.mrjalal.topnews.presentation.home.screen.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrjalal.topnews.domain.repository.model.NewsUiModel
import com.mrjalal.topnews.presentation.app.ui.theme.Gray_2
import com.mrjalal.topnews.presentation.app.ui.theme.Gray_4
import com.mrjalal.topnews.presentation.common.component.TopNewsRemoteImage
import com.mrjalal.topnews.presentation.common.helper.noRippleClickable

@Composable
fun NewsItem(
    item: NewsUiModel.NewsItemUiModel,
    onItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable {
                Log.d("oio", "NewsItem:> ID: ${item.id.toString()}")
                onItemClick(item.id.toString())
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        TopNewsRemoteImage(
            url = item.urlToImage ?: "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .background(
                    color = Gray_4,
                    shape = RoundedCornerShape(8.dp)
                )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            Text(
                text = item.author ?: "",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Gray_2
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = item.title ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = item.publishedAt,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Gray_2
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsItemPrev() {
    NewsItem(
        item = NewsUiModel.NewsItemUiModel.PREVIEW,
        onItemClick = {}
    )
}