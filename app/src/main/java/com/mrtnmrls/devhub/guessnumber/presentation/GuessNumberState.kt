package com.mrtnmrls.devhub.guessnumber.presentation

data class GuessNumberState(
    val uiScreenState: GuessNumberUiState = GuessNumberUiState.Instructions
)

sealed class GuessNumberUiState {
    data object Instructions : GuessNumberUiState()
    data class GameInProgress(
        val attempt: Int = 0,
        val numberCorrect: Int = 0,
        val hint: String = ""
    ) : GuessNumberUiState()
    data class GameOver(val totalAttempts: Int = 0) : GuessNumberUiState()
    data class GameWin(val totalAttempts: Int = 0) : GuessNumberUiState()

    val asSafeGameInProgress: GameInProgress
        get() = this as? GameInProgress ?: GameInProgress()
}

// instructions -> game in progress -> game over -> game in progress -> game win
// instructions -> game in progress -> game win -> game in progress -> game over