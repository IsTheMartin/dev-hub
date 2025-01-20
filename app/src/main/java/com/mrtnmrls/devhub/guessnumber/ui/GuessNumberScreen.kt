package com.mrtnmrls.devhub.guessnumber.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mrtnmrls.devhub.common.ui.view.DevTopAppBar
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberEvent
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberState
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberUiState
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberViewModel

@Composable
fun GuessNumberContainer(navController: NavHostController) {
    val viewModel = hiltViewModel<GuessNumberViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            DevTopAppBar("Guess the number") {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        GuessNumberScreen(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = viewModel::handleEvents
        )
    }
}

@Composable
fun GuessNumberScreen(
    modifier: Modifier = Modifier,
    state: GuessNumberState,
    onEvent: (GuessNumberEvent) -> Unit
) {
    when (val uiState = state.uiScreenState) {
        is GuessNumberUiState.GameInProgress -> GameInProgressView(
            modifier = modifier,
            attempts = uiState.attempt,
            hint = uiState.hint,
            onEvent = onEvent
        )

        is GuessNumberUiState.GameOver -> GameOverView(
            modifier = modifier,
            attempts = uiState.totalAttempts,
            onEvent = onEvent
        )

        is GuessNumberUiState.GameWin -> GameWinView(
            modifier = modifier,
            attempts = uiState.totalAttempts,
            onEvent = onEvent
        )

        GuessNumberUiState.Instructions -> InstructionsView(
            modifier = modifier,
            onEvent = onEvent
        )
    }
}

@Composable
fun InstructionsView(modifier: Modifier = Modifier, onEvent: (GuessNumberEvent) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Try to guess the number, you have an infinite attempts")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEvent(GuessNumberEvent.OnNewGame) }
        ) {
            Text("Start a new game!")
        }
    }
}

@Composable
fun GameWinView(modifier: Modifier = Modifier, attempts: Int, onEvent: (GuessNumberEvent) -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You have won in $attempts attempts")
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEvent(GuessNumberEvent.OnNewGame) }
        ) {
            Text("Start a new game!")
        }
    }
}

@Composable
fun GameOverView(
    modifier: Modifier = Modifier,
    attempts: Int,
    onEvent: (GuessNumberEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Game is over, you lost in $attempts attempts")
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEvent(GuessNumberEvent.OnNewGame) }
        ) {
            Text("Start a new game!")
        }
    }
}


@Composable
private fun GameInProgressView(
    modifier: Modifier = Modifier,
    attempts: Int,
    hint: String,
    onEvent: (GuessNumberEvent) -> Unit
) {
    var number by remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Attempts: $attempts")
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                showKeyboardOnFocus = true
            )
        )
        Text(text = hint)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEvent(GuessNumberEvent.OnAttempt(number)) }
        ) {
            Text(text = "Guess")
        }
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEvent(GuessNumberEvent.OnSurrender) }
        ) {
            Text(text = "Surrender")
        }
    }
}