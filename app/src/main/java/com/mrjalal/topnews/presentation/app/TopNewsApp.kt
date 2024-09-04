package com.mrjalal.topnews.presentation.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mrjalal.topnews.presentation.home.screen.navigation.HOME_ROUTE
import com.mrjalal.topnews.presentation.home.screen.navigation.homeScreen
import com.mrjalal.topnews.presentation.newsDetail.screen.navigation.navigateToNewsDetail
import com.mrjalal.topnews.presentation.newsDetail.screen.navigation.newsDetailScreen

@Composable
fun TopNewsApp() {
    // todo (keep navController in uiState of App)
    val navController = rememberNavController()
    NavigationGraph(navController)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(150)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(150)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(150)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(150)
            )
        },
//        modifier = Modifier.safeDrawingPadding()
    ) {
        val onBack: () -> Unit = { navController.navigateUp() }
        val onNavigateToNewsDetail: (String) -> Unit = {
            navController.navigateToNewsDetail(it)
        }

        homeScreen(
            onNavigateToNewsDetail
        )
        newsDetailScreen(
            onBack
        )
    }
}
