package com.mrjalal.topnews.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mrjalal.topnews.presentation.app.ui.theme.Gray_2
import com.mrjalal.topnews.presentation.common.helper.screenWidthDp
import com.valentinilk.shimmer.shimmer

@Composable
fun TopNewsShimmerLoading(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White
) {
    val screenWidth = screenWidthDp().dp - 20.dp * 2
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 16.dp, horizontal = 20.dp),
    ) {
        Box(
            modifier = Modifier
                .requiredHeight(20.dp)
                .requiredWidth(screenWidth / 3)
                .clip(RoundedCornerShape(8.dp))
                .shimmer()
                .background(Gray_2)
        )

        Spacer(modifier = Modifier.requiredSize(8.dp))

        Box(
            modifier = Modifier
                .requiredHeight(20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .shimmer()
                .background(Gray_2)
        )

        Spacer(modifier = Modifier.requiredSize(8.dp))

        Box(
            modifier = Modifier
                .requiredHeight(20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .shimmer()
                .background(Gray_2)
        )

        Spacer(modifier = Modifier.requiredSize(8.dp))

        Box(
            modifier = Modifier
                .requiredHeight(20.dp)
                .requiredWidth(screenWidth / 2)
                .clip(RoundedCornerShape(8.dp))
                .shimmer()
                .background(Gray_2)
        )
    }
}