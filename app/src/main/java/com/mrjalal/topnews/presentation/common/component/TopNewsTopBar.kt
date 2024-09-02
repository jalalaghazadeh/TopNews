package com.mrjalal.topnews.presentation.common.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mrjalal.topnews.presentation.app.ui.theme.Gray
import com.mrjalal.topnews.presentation.app.ui.theme.Gray4
import com.mrjalal.topnews.presentation.app.ui.theme.Gray_12
import com.mrjalal.topnews.presentation.app.ui.theme.Gray_7

@Composable
fun TopNewsTopBar(
    title: String,
    @DrawableRes rightIconId: Int? = null,
    @DrawableRes leftIconId: Int? = null,
    onRightIconClick: (() -> Unit)? = null,
    onLeftClick: (() -> Unit)? = null,
    backgroundColor: Color = Color.White,
    iconBackgroundColor: Color = Gray_12
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        rightIconId?.let {
            Image(
                painter = painterResource(rightIconId),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRightIconClick?.let { onClick -> onClick() } }
            )
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge.copy(color = Gray4, fontWeight = FontWeight.Bold))
        }
        leftIconId?.let {
            TopNewsIcon(
                iconId = leftIconId,
                modifier = Modifier.size(20.dp),
                tintColor = Gray,
                containerModifier = Modifier
                    .size(32.dp)
                    .background(
                        color = iconBackgroundColor,
                        shape = CircleShape
                    )
                    .clickable {
                        onLeftClick?.let { onClick -> onClick() }
                    }
            )
        }
    }
}