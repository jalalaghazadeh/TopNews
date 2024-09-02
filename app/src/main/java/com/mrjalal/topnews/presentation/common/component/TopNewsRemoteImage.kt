package com.mrjalal.topnews.presentation.common.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun TopNewsRemoteImage(
    modifier: Modifier = Modifier,
    url: String,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    colorFilter: ColorFilter? = null,
) {
    val context = LocalContext.current

    val model = ImageRequest.Builder(context)
        .apply {
            data(url)
            decoderFactory(SvgDecoder.Factory())
            diskCacheKey(url)
            memoryCacheKey(url)
            crossfade(true)
            crossfade(100)
        }.build()

    AsyncImage(
        modifier = modifier,
        model = model,
        contentDescription = contentDescription,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}
