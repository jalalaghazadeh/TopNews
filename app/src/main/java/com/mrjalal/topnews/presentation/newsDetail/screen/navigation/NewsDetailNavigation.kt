package com.mrjalal.topnews.presentation.newsDetail.screen.navigation

import android.icu.text.UnicodeSetIterator
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mrjalal.topnews.presentation.newsDetail.screen.NewsDetailRoute

const val NEWS_ID = "id"
const val NEWS_DETAIL_ROUTE = "NewsDetail?$NEWS_ID={$NEWS_ID}"

fun NavController.navigateToNewsDetail(id: String) = navigate("NewsDetail?$NEWS_ID=$id")

fun NavGraphBuilder.newsDetailScreen(
    onBack: () -> Unit
){
    composable(route = NEWS_DETAIL_ROUTE) {
        val newsId = it.arguments?.getString(NEWS_ID)
        NewsDetailRoute(
            newsId = newsId,
            onBack = onBack
        )
    }
}