package com.mrjalal.topnews.presentation.home.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mrjalal.topnews.presentation.home.screen.HomeRoute

const val HOME_ROUTE = "Home"
fun NavController.navigateToHome() = navigate(HOME_ROUTE)

fun NavGraphBuilder.homeScreen() {
    composable(route = HOME_ROUTE) {
        HomeRoute()
    }
}
