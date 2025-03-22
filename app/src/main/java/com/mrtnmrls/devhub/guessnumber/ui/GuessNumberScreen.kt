package com.mrtnmrls.devhub.guessnumber.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mrtnmrls.devhub.R
import com.mrtnmrls.devhub.common.ui.compositionlocal.LocalNavController
import com.mrtnmrls.devhub.common.ui.view.DevTopAppBar
import com.mrtnmrls.devhub.common.ui.view.PrimaryButton
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberEvent
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberState
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberUiState
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberViewModel

@Composable
fun GuessNumberContainer() {
    val navController = LocalNavController.current
    val viewModel = hiltViewModel<GuessNumberViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .imePadding(),
        topBar = {
            DevTopAppBar("Guess the number") {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        GuessNumberScreen(
            modifier = Modifier
                .padding(paddingValues),
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
            attempts = uiState.gameState.attempts,
            feedback = uiState.gameState.feedback,
            history = uiState.gameState.history,
            onEvent = onEvent
        )

        is GuessNumberUiState.GameOver -> GameOverView(
            modifier = modifier,
            targetNumber = uiState.gameState.targetNumber,
            attempts = uiState.gameState.attempts,
            onEvent = onEvent
        )

        is GuessNumberUiState.GameWin -> GameWinView(
            modifier = modifier,
            targetNumber = uiState.gameState.targetNumber,
            attempts = uiState.gameState.attempts,
            onEvent = onEvent
        )

        GuessNumberUiState.Instructions -> InstructionsView(
            modifier = modifier,
            onEvent = onEvent
        )
    }
}

@Composable
fun StartNewGameButton(modifier: Modifier = Modifier, onEvent: (GuessNumberEvent) -> Unit) {
    PrimaryButton(
        modifier = modifier
            .fillMaxWidth(),
        buttonText = stringResource(R.string.guess_number_new_game),
        onClick = { onEvent(GuessNumberEvent.OnNewGame) }
    )
}
