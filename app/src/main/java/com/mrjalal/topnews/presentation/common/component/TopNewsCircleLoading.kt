package com.mrjalal.topnews.presentation.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mrjalal.topnews.presentation.app.ui.theme.Gray

@Composable
fun TopNewsCircleLoading(
    modifier: Modifier = Modifier,
    color: Color = Gray,
    strokeWidth: Dp = 2.dp,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(18.dp).then(modifier),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}