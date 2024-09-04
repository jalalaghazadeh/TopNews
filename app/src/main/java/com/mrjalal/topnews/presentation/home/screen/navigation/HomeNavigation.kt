package com.mrjalal.topnews.presentation.home.screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mrjalal.topnews.presentation.home.screen.HomeRoute

const val HOME_ROUTE = "Home"
fun NavController.navigateToHome() = navigate(HOME_ROUTE)

fun NavGraphBuilder.homeScreen(onNavigateToNewsDetail: (String) -> Unit) {
    composable(route = HOME_ROUTE) {
        HomeRoute(onNavigateToNewsDetail = onNavigateToNewsDetail)
    }
}
