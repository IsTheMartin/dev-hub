package com.mrtnmrls.devhub.common.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mrtnmrls.devhub.common.ui.compositionlocal.LocalNavController
import com.mrtnmrls.devhub.esp8266.ui.Esp8266Container
import com.mrtnmrls.devhub.guessnumber.ui.GuessNumberContainer
import com.mrtnmrls.devhub.landing.presentation.Esp8266Screen
import com.mrtnmrls.devhub.landing.presentation.GuessNumberScreen
import com.mrtnmrls.devhub.landing.presentation.LandingScreen
import com.mrtnmrls.devhub.landing.presentation.LazyMindMapScreen
import com.mrtnmrls.devhub.landing.presentation.LoginScreen
import com.mrtnmrls.devhub.landing.presentation.PullToRefreshScreen
import com.mrtnmrls.devhub.landing.presentation.TodoListScreen
import com.mrtnmrls.devhub.landing.presentation.WebScraperScreen
import com.mrtnmrls.devhub.landing.ui.LandingContainer
import com.mrtnmrls.devhub.lazymindmap.ui.LazyMindMapContainer
import com.mrtnmrls.devhub.login.ui.LoginContainer
import com.mrtnmrls.devhub.pulltorefresh.ui.PullToRefreshContainer
import com.mrtnmrls.devhub.todolist.ui.TodoListContainer
import com.mrtnmrls.devhub.webscraping.presentation.screens.WebScraperContainer

@Composable
fun DevHubContainer() {
    val navController = LocalNavController.current

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = LoginScreen
    ) {
        composable<LoginScreen> {
            LoginContainer()
        }
        composable<LandingScreen> {
            LandingContainer()
        }
        composable<PullToRefreshScreen> {
            PullToRefreshContainer()
        }
        composable<Esp8266Screen> {
            Esp8266Container()
        }
        composable<TodoListScreen> {
            TodoListContainer()
        }
        composable<GuessNumberScreen> {
            GuessNumberContainer()
        }
        composable<LazyMindMapScreen> {
            LazyMindMapContainer()
        }
        composable<WebScraperScreen> {
            WebScraperContainer()
        }
    }
}
