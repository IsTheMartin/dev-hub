package com.mrtnmrls.devhub.guessnumber.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GuessNumberViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(GuessNumberState())
    val state = _state.asStateFlow()

    fun handleEvents(event: GuessNumberEvent) {
        when (event) {
            GuessNumberEvent.OnPreMatch -> displayInstructions()
            is GuessNumberEvent.OnAttempt -> attempt(event.number)
            GuessNumberEvent.OnNewGame -> startNewGame()
            GuessNumberEvent.OnSurrender -> surrender()
        }
    }

    private fun displayInstructions() {
        _state.update {
            it.copy(uiScreenState = GuessNumberUiState.Instructions)
        }
    }

    private fun attempt(number: String) {
        val gameInProgress = _state.value.uiScreenState.asSafeGameInProgress
        val newAttemptValue = gameInProgress.attempt + 1
        if (number.toInt() == gameInProgress.numberCorrect) {
            _state.update {
                it.copy(uiScreenState = GuessNumberUiState.GameWin(newAttemptValue))
            }
        } else {
            val hint = if (gameInProgress.numberCorrect > number.toInt()) {
                "is greater"
            } else {
                "is lower"
            }
            _state.update {
                it.copy(
                    uiScreenState = _state.value.uiScreenState.asSafeGameInProgress.copy(
                        attempt = newAttemptValue,
                        hint = hint
                    )
                )
            }
        }
    }

    private fun startNewGame() {
        val randomNumber = (1..101).random()
        _state.update {
            it.copy(
                uiScreenState = GuessNumberUiState.GameInProgress(
                    attempt = 0,
                    numberCorrect = randomNumber
                )
            )
        }
    }

    private fun surrender() {
        val gameInProgress = _state.value.uiScreenState.asSafeGameInProgress
        _state.update {
            it.copy(uiScreenState = GuessNumberUiState.GameOver(gameInProgress.attempt))
        }
    }

}