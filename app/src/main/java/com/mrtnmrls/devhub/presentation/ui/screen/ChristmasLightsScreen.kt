@file:OptIn(ExperimentalMaterial3Api::class)

package com.mrtnmrls.devhub.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mrtnmrls.devhub.presentation.common.LoadingLottieView
import com.mrtnmrls.devhub.presentation.ui.state.Esp8266ScreenState
import com.mrtnmrls.devhub.presentation.ui.theme.CetaceanBlue
import com.mrtnmrls.devhub.presentation.viewmodel.Esp8266ViewModel
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
internal fun ChristmasLightsContainer(
    navController: NavHostController
) {
    val esp8266ViewModel = hiltViewModel<Esp8266ViewModel>()
    val state by esp8266ViewModel.state.collectAsState()
    ChristmasLightsScreen(
        navController = navController,
        onChange = { esp8266ViewModel.toggleChristmasLightsState() },
        uiState = state.uiState
    )
}

@Composable
internal fun ChristmasLightsScreen(
    navController: NavController,
    onChange: () -> Unit,
    uiState: Esp8266ScreenState
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CetaceanBlue
                ),
                title = { Text(text = "ESP8266") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when (uiState) {
            Esp8266ScreenState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color.Red)
                )
            }

            Esp8266ScreenState.Loading -> {
                LoadingLottieView(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(CetaceanBlue)
                )
            }

            is Esp8266ScreenState.SuccessfulContent -> {
                ContentView(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(CetaceanBlue),
                    state = uiState,
                    onToggleChristmasLights = onChange
                )
            }
        }
    }
}

@Composable
private fun ContentView(
    modifier: Modifier = Modifier,
    state: Esp8266ScreenState.SuccessfulContent,
    onToggleChristmasLights: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Last update: ${state.values.currentDateTime}",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Pin state: ${if (state.values.pinState) "On" else "Off"}",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Time alive: ${state.values.timeAlive.toDuration(DurationUnit.MINUTES)} minutes",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Christmas lights: ${if (state.values.christmasLights) "On" else "Off"}",
                style = MaterialTheme.typography.bodyLarge
            )
            Button(
                onClick = { onToggleChristmasLights() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = CetaceanBlue,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary,
                    disabledContentColor = CetaceanBlue
                )
            ) {
                Text(text = if (state.values.christmasLights) "Turn off" else "Turn on")
            }
            Switch(
                checked = state.values.christmasLights,
                onCheckedChange = { newState -> onToggleChristmasLights() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}
