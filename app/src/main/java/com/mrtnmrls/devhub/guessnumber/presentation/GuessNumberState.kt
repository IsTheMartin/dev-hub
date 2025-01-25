package com.mrtnmrls.devhub.guessnumber.presentation

import com.mrtnmrls.devhub.guessnumber.domain.model.GuessNumberGameState

data class GuessNumberState(
    val uiScreenState: GuessNumberUiState = GuessNumberUiState.Instructions
)

sealed class GuessNumberUiState {
    data object Instructions : GuessNumberUiState()
    data class GameInProgress(
        val gameState: GuessNumberGameState = GuessNumberGameState()
    ) : GuessNumberUiState()
    data class GameOver(
        val gameState: GuessNumberGameState
    ) : GuessNumberUiState()
    data class GameWin(
        val gameState: GuessNumberGameState
    ) : GuessNumberUiState()

    val asSafeGameInProgress: GameInProgress
        get() = this as? GameInProgress ?: GameInProgress()
}
