package com.mrtnmrls.devhub.guessnumber.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrtnmrls.devhub.guessnumber.domain.usecase.GuessNumberUseCase
import com.mrtnmrls.devhub.guessnumber.domain.usecase.ResetGuessNumberStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuessNumberViewModel @Inject constructor(
    private val guessNumberUseCase: GuessNumberUseCase,
    private val resetGuessNumberStateUseCase: ResetGuessNumberStateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GuessNumberState())
    val state = _state.asStateFlow()

    fun handleEvents(event: GuessNumberEvent) {
        when (event) {
            GuessNumberEvent.OnPreMatch -> displayInstructions()
            is GuessNumberEvent.OnGuess -> guess(event.number)
            GuessNumberEvent.OnNewGame -> startNewGame()
            GuessNumberEvent.OnSurrender -> surrender()
        }
    }

    private fun displayInstructions() {
        _state.update {
            it.copy(uiScreenState = GuessNumberUiState.Instructions)
        }
    }

    private fun guess(number: String) {
        if (number.isNotEmpty()) {
            val gameInProgress = _state.value.uiScreenState.asSafeGameInProgress
            val updatedGameState = guessNumberUseCase(gameInProgress.gameState, number.toInt())
            if (updatedGameState.isWinner) {
                _state.update {
                    it.copy(uiScreenState = GuessNumberUiState.GameWin(updatedGameState))
                }
            } else {
                _state.update {
                    it.copy(uiScreenState = GuessNumberUiState.GameInProgress(updatedGameState))
                }
            }
        }
    }

    private fun startNewGame() = viewModelScope.launch {
        val newGameState = resetGuessNumberStateUseCase()
        _state.update {
            it.copy(
                uiScreenState = GuessNumberUiState.GameInProgress(newGameState)
            )
        }
    }

    private fun surrender() {
        val gameInProgress = _state.value.uiScreenState.asSafeGameInProgress
        _state.update {
            it.copy(uiScreenState = GuessNumberUiState.GameOver(gameInProgress.gameState))
        }
    }
}
