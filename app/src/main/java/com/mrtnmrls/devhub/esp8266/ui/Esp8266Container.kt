@file:OptIn(ExperimentalMaterial3Api::class)

package com.mrtnmrls.devhub.esp8266.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mrtnmrls.devhub.common.ui.compositionlocal.LocalNavController
import com.mrtnmrls.devhub.common.ui.view.DevTopAppBar
import com.mrtnmrls.devhub.common.ui.view.LoadingLottieView
import com.mrtnmrls.devhub.common.ui.view.VerticalSpacer
import com.mrtnmrls.devhub.esp8266.domain.model.Esp8266Values
import com.mrtnmrls.devhub.esp8266.presentation.Esp8266ScreenState
import com.mrtnmrls.devhub.esp8266.presentation.Esp8266ViewModel
import com.mrtnmrls.devhub.common.ui.theme.AzureishWhite
import com.mrtnmrls.devhub.common.ui.theme.CadetBlue
import com.mrtnmrls.devhub.common.ui.theme.DarkElectricBlue
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.common.ui.theme.JapaneseIndigo
import com.mrtnmrls.devhub.common.ui.theme.Typography
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
internal fun Esp8266Container() {
    val navController = LocalNavController.current
    val esp8266ViewModel = hiltViewModel<Esp8266ViewModel>()
    val state by esp8266ViewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DevTopAppBar(
                title = "ESP8266"
            ) { navController.navigateUp() }
        }
    ) { paddingValues ->
        Esp8266Screen(
            modifier = Modifier.padding(paddingValues),
            onChange = { esp8266ViewModel.toggleChristmasLightsState() },
            uiState = state.uiState
        )
    }
}

@Composable
internal fun Esp8266Screen(
    modifier: Modifier = Modifier,
    onChange: () -> Unit,
    uiState: Esp8266ScreenState
) {
    when (uiState) {
        Esp8266ScreenState.Error -> {
            Box(
                modifier = modifier
                    .background(CadetBlue)
            )
        }

        Esp8266ScreenState.Loading -> {
            LoadingLottieView(
                modifier = modifier
                    .background(AzureishWhite)
            )
        }

        is Esp8266ScreenState.SuccessfulContent -> {
            ContentView(
                modifier = modifier
                    .background(AzureishWhite),
                state = uiState,
                onToggleChristmasLights = onChange
            )
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
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LastUpdateView(lastUpdate = state.values.currentDateTime)
            PinStateView(pinState = state.values.pinState)
            TimeAliveView(timeAlive = state.values.timeAlive)
            ChristmasLightsView(lightsState = state.values.christmasLights) {
                onToggleChristmasLights()
            }
        }
    }
}

@Composable
private fun LastUpdateView(modifier: Modifier = Modifier, lastUpdate: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(CadetBlue)
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = "Last update",
                style = Typography.bodyLarge,
                color = JapaneseIndigo
            )
            VerticalSpacer(8.dp)
            Text(
                text = lastUpdate,
                style = Typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = JapaneseIndigo
            )
        }
    }
}

@Composable
private fun PinStateView(modifier: Modifier = Modifier, pinState: Boolean) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(getPinStateCardBackgroundColor(pinState))
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = "Pin state",
                style = Typography.bodyLarge,
                color = JapaneseIndigo
            )
            VerticalSpacer(8.dp)
            Text(
                text = if (pinState) "On" else "Off",
                style = Typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = JapaneseIndigo
            )
        }
    }
}

@Composable
private fun TimeAliveView(modifier: Modifier = Modifier, timeAlive: Long) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(CadetBlue)
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = "Time alive",
                style = Typography.bodyLarge,
                color = JapaneseIndigo
            )
            VerticalSpacer(8.dp)
            Text(
                text = timeAlive.toDuration(DurationUnit.MINUTES).toString(),
                style = Typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = JapaneseIndigo
            )
        }
    }
}

@Composable
private fun ChristmasLightsView(
    modifier: Modifier = Modifier,
    lightsState: Boolean,
    onToggleChristmasLights: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(getPinStateCardBackgroundColor(lightsState))
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = "Christmas lights state",
                style = Typography.bodyLarge,
                color = JapaneseIndigo
            )
            VerticalSpacer(8.dp)
            Text(
                text = if (lightsState) "On" else "Off",
                style = Typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = JapaneseIndigo
            )
            Switch(
                checked = lightsState,
                onCheckedChange = { onToggleChristmasLights() },
                colors = SwitchDefaults.colors(
                    checkedBorderColor = JapaneseIndigo,
                    checkedThumbColor = JapaneseIndigo,
                    checkedTrackColor = AzureishWhite,
                    uncheckedThumbColor = DarkElectricBlue,
                    uncheckedBorderColor = JapaneseIndigo,
                    uncheckedTrackColor = AzureishWhite,
                )
            )
        }
    }
}

private fun getPinStateCardBackgroundColor(pinState: Boolean) =
    if (pinState) CadetBlue else DarkElectricBlue

@Preview
@Composable
private fun PreviewContentView() {
    DevhubTheme {
        Surface {
            ContentView(
                state = Esp8266ScreenState.SuccessfulContent(
                    Esp8266Values(
                        christmasLights = true,
                        currentDateTime = "2025-01-24T23:30:00",
                        pinState = true,
                        timeAlive = 1239123L
                    )
                )
            ) { }
        }
    }
}
