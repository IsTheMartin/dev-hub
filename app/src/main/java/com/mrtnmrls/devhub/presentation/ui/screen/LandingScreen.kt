package com.mrtnmrls.devhub.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mrtnmrls.devhub.presentation.common.ProfileDialog
import com.mrtnmrls.devhub.presentation.ui.effect.LandingEffect
import com.mrtnmrls.devhub.presentation.ui.intent.DevHubRouteIntent
import com.mrtnmrls.devhub.presentation.ui.intent.LandingIntent
import com.mrtnmrls.devhub.presentation.ui.route.DevHubRoute
import com.mrtnmrls.devhub.presentation.ui.route.navigateToChristmasLightsScreen
import com.mrtnmrls.devhub.presentation.ui.route.navigateToPullToRefreshScreen
import com.mrtnmrls.devhub.presentation.ui.state.LandingState
import com.mrtnmrls.devhub.presentation.ui.state.ProfileDialogState
import com.mrtnmrls.devhub.presentation.ui.theme.Camel
import com.mrtnmrls.devhub.presentation.ui.theme.CetaceanBlue
import com.mrtnmrls.devhub.presentation.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.presentation.ui.theme.Khaki
import com.mrtnmrls.devhub.presentation.ui.theme.MetallicBlue
import com.mrtnmrls.devhub.presentation.ui.theme.MorningBlue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    state: LandingState,
    onIntent: (DevHubRouteIntent) -> Unit,
    onLandingIntent: (LandingIntent) -> Unit
) {

    var isProfileDialogVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.background(MetallicBlue),
        topBar = {
            TopAppBar(
                title = { Text(text = "DevHub") },
                colors = TopAppBarColors(
                    containerColor = MetallicBlue,
                    titleContentColor = Khaki,
                    scrolledContainerColor = Khaki,
                    navigationIconContentColor = Khaki,
                    actionIconContentColor = Khaki
                ),
                actions = {
                    IconButton(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        onClick = { onLandingIntent(LandingIntent.OnProfileDialogShow) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = ""
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(50.dp),
                contentColor = CetaceanBlue,
                containerColor = MetallicBlue
            ) {
                Text(text = "Made by Martin with 🧠", color = Khaki)
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(MorningBlue)
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
            containerColor = Camel,
            contentColor = CetaceanBlue,
            disabledContentColor = Khaki,
            disabledContainerColor = MorningBlue
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
