package com.mrtnmrls.devhub.common.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrtnmrls.devhub.landing.presentation.DevHubRoute
import com.mrtnmrls.devhub.esp8266.ui.Esp8266Container
import com.mrtnmrls.devhub.guessnumber.ui.GuessNumberContainer
import com.mrtnmrls.devhub.landing.ui.LandingContainer
import com.mrtnmrls.devhub.login.ui.LoginContainer
import com.mrtnmrls.devhub.pulltorefresh.ui.PullToRefreshContainer
import com.mrtnmrls.devhub.todolist.ui.TodoListContainer

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
            PullToRefreshContainer(navController)
        }
        composable(DevHubRoute.ChristmasLights.route) {
            Esp8266Container(navController)
        }
        composable(DevHubRoute.TodoList.route) {
            TodoListContainer(navController)
        }
        composable(DevHubRoute.GuessNumber.route) {
            GuessNumberContainer(navController)
        }
    }
}
