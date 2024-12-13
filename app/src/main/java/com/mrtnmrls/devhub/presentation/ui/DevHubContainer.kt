package com.mrtnmrls.devhub.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrtnmrls.devhub.presentation.ui.route.DevHubRoute
import com.mrtnmrls.devhub.presentation.ui.screen.ChristmasLightsContainer
import com.mrtnmrls.devhub.presentation.ui.screen.LandingContainer
import com.mrtnmrls.devhub.presentation.ui.screen.LoginContainer
import com.mrtnmrls.devhub.presentation.ui.screen.PullToRefreshContainer

@Composable
fun DevHubContainer() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = DevHubRoute.Login.route
    ) {
        composable(DevHubRoute.Login.route) {
            LoginContainer(navController)
        }
        composable(DevHubRoute.Landing.route) {
            LandingContainer(navController)
        }
        composable(DevHubRoute.PullToRefresh.route) {
            PullToRefreshContainer()
        }
        composable(DevHubRoute.ChristmasLights.route) {
            ChristmasLightsContainer(navController)
        }
    }
}
