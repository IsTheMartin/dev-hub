package com.mrtnmrls.devhub.landing.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mrtnmrls.devhub.common.ui.compositionlocal.LocalActivity
import com.mrtnmrls.devhub.common.ui.compositionlocal.LocalNavController
import com.mrtnmrls.devhub.common.ui.view.BottomAppBarView
import com.mrtnmrls.devhub.login.ui.ProfileDialog
import com.mrtnmrls.devhub.login.ui.TopAppBarView
import com.mrtnmrls.devhub.landing.presentation.LandingEffect
import com.mrtnmrls.devhub.landing.presentation.DevHubRouteIntent
import com.mrtnmrls.devhub.landing.presentation.LandingIntent
import com.mrtnmrls.devhub.landing.presentation.LandingState
import com.mrtnmrls.devhub.landing.presentation.ProfileDialogState
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.landing.presentation.Esp8266Screen
import com.mrtnmrls.devhub.landing.presentation.GuessNumberScreen
import com.mrtnmrls.devhub.landing.presentation.LandingViewModel
import com.mrtnmrls.devhub.landing.presentation.LazyMindMapScreen
import com.mrtnmrls.devhub.landing.presentation.LoginScreen
import com.mrtnmrls.devhub.landing.presentation.PullToRefreshScreen
import com.mrtnmrls.devhub.landing.presentation.TodoListScreen

@Composable
fun LandingContainer() {
    val navController = LocalNavController.current
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
        DevHubRouteIntent.OnESP8266Clicked -> navController.navigate(Esp8266Screen)
        DevHubRouteIntent.OnPullToRefreshClicked -> navController.navigate(PullToRefreshScreen)
        DevHubRouteIntent.OnTodoListClicked -> navController.navigate(TodoListScreen)
        DevHubRouteIntent.OnGuessNumberClicked -> navController.navigate(GuessNumberScreen)
        DevHubRouteIntent.OnLazyMindMapClicked -> navController.navigate(LazyMindMapScreen)
    }
}

@Composable
fun HandleLandingEffects(effect: LandingEffect, navController: NavHostController) {
    LaunchedEffect(effect) {
        when (effect) {
            LandingEffect.NoOp -> Unit
            LandingEffect.OnSuccessfulSignOut -> {
                navController.navigate(LoginScreen) {
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
    val activity = LocalActivity.current
    var isProfileDialogVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
        ,
        topBar = {
            TopAppBarView { onLandingIntent(it) }
        },
        bottomBar = { BottomAppBarView() }
    ) { paddingValues ->
        activity.enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.dark(MaterialTheme.colorScheme.secondaryContainer.toArgb())
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
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
            item {
                LandingButton(text = "Guess the number") {
                    onIntent(DevHubRouteIntent.OnGuessNumberClicked)
                }
            }
            item {
                LandingButton(text = "Lazy mind map") {
                    onIntent(DevHubRouteIntent.OnLazyMindMapClicked)
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
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(text = text)
    }

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    showSystemUi = true
)
@Composable
private fun PreviewLandingScreenDark() {
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

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showSystemUi = true
)
@Composable
private fun PreviewLandingScreenLight() {
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
