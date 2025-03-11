package com.mrtnmrls.devhub.landing.presentation

import androidx.navigation.NavHostController

sealed class DevHubRoute(val route: String) {
    data object Login : DevHubRoute("login")
    data object Landing : DevHubRoute("landing")
    data object PullToRefresh : DevHubRoute("pull_to_refresh")
    data object ChristmasLights : DevHubRoute("christmas_lights")
    data object TodoList : DevHubRoute("todo_list")
    data object GuessNumber : DevHubRoute("guess_number")
    data object LazyMindMap : DevHubRoute("lazy_mind_map")
}

fun navigateToLandingScreen(navController: NavHostController) =
    navController.navigate(DevHubRoute.Landing.route) {
        launchSingleTop = true
        popUpTo(DevHubRoute.Login.route) { inclusive = true }
    }

fun navigateToPullToRefreshScreen(navController: NavHostController) =
    navController.navigate(DevHubRoute.PullToRefresh.route)

fun navigateToChristmasLightsScreen(navController: NavHostController) =
    navController.navigate(DevHubRoute.ChristmasLights.route)

fun navigateToTodoListScreen(navController: NavHostController) =
    navController.navigate(DevHubRoute.TodoList.route)

fun navigateToGuessNumberScreen(navController: NavHostController) =
    navController.navigate(DevHubRoute.GuessNumber.route)

fun navigateToLazyMindMapScreen(navController: NavHostController) =
    navController.navigate(DevHubRoute.LazyMindMap.route)
