package com.mrtnmrls.devhub.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mrtnmrls.devhub.presentation.common.BottomAppBarView
import com.mrtnmrls.devhub.presentation.common.ProfileDialog
import com.mrtnmrls.devhub.presentation.common.TopAppBarView
import com.mrtnmrls.devhub.presentation.ui.effect.LandingEffect
import com.mrtnmrls.devhub.presentation.ui.intent.DevHubRouteIntent
import com.mrtnmrls.devhub.presentation.ui.intent.LandingIntent
import com.mrtnmrls.devhub.presentation.ui.route.DevHubRoute
import com.mrtnmrls.devhub.presentation.ui.route.navigateToChristmasLightsScreen
import com.mrtnmrls.devhub.presentation.ui.route.navigateToPullToRefreshScreen
import com.mrtnmrls.devhub.presentation.ui.route.navigateToTodoListScreen
import com.mrtnmrls.devhub.presentation.ui.state.LandingState
import com.mrtnmrls.devhub.presentation.ui.state.ProfileDialogState
import com.mrtnmrls.devhub.presentation.ui.theme.AzureishWhite
import com.mrtnmrls.devhub.presentation.ui.theme.CadetBlue
import com.mrtnmrls.devhub.presentation.ui.theme.DarkElectricBlue
import com.mrtnmrls.devhub.presentation.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.presentation.viewmodel.LandingViewModel

@Composable
fun LandingContainer(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<LandingViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    LandingScreen(
        state = state,
        onLandingIntent = viewModel::dispatchIntent,
        onIntent = { handleLandingIntents(it, navController) }
    )
    HandleLandingEffects(state.effect, navController)
}

fun handleLandingIntents(intent: DevHubRouteIntent, navController: NavHostController) {
    when (intent) {
        DevHubRouteIntent.OnESP8266Clicked -> navigateToChristmasLightsScreen(navController)
        DevHubRouteIntent.OnPullToRefreshClicked -> navigateToPullToRefreshScreen(navController)
        DevHubRouteIntent.OnTodoListClicked -> navigateToTodoListScreen(navController)
    }
}

@Composable
fun HandleLandingEffects(effect: LandingEffect, navController: NavHostController) {
    LaunchedEffect(effect) {
        when (effect) {
            LandingEffect.NoOp -> Unit
            LandingEffect.OnSuccessfulSignOut -> {
                navController.navigate(DevHubRoute.Login.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}

@Composable
fun LandingScreen(
    state: LandingState,
    onIntent: (DevHubRouteIntent) -> Unit,
    onLandingIntent: (LandingIntent) -> Unit
) {

    var isProfileDialogVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarView { onLandingIntent(it) }
        },
        bottomBar = { BottomAppBarView() }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(AzureishWhite)
                .padding(paddingValues),
            columns = GridCells.Fixed(2)
        ) {
            item {
                LandingButton(text = "Pull to Refresh") {
                    onIntent(DevHubRouteIntent.OnPullToRefreshClicked)
                }
            }
            item {
                LandingButton(text = "ESP8266") {
                    onIntent(DevHubRouteIntent.OnESP8266Clicked)
                }
            }
            item {
                LandingButton(text = "Todo list") {
                    onIntent(DevHubRouteIntent.OnTodoListClicked)
                }
            }
        }
    }

    LaunchedEffect(key1 = state.profileDialogState) {
        isProfileDialogVisible = when(state.profileDialogState) {
            ProfileDialogState.Hidden -> false
            ProfileDialogState.Visible -> true
        }
    }

    if (isProfileDialogVisible) {
        ProfileDialog(
            userProfileState = state.userProfileState
        ) {
            onLandingIntent(it)
        }
    }
}

@Composable
private fun LandingButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) =
    Button(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonColors(
            containerColor = CadetBlue,
            contentColor = Color.Black,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.Gray
        )
    ) {
        Text(text = text)
    }

@Preview
@Composable
private fun PreviewLandingScreen() {
    DevhubTheme {
        Surface {
            LandingScreen(
                state = LandingState(),
                onIntent = { },
                onLandingIntent = { }
            )
        }
    }
}
