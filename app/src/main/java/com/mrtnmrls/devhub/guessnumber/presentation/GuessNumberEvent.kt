package com.mrtnmrls.devhub.guessnumber.presentation

sealed interface GuessNumberEvent {
    data object OnPreMatch : GuessNumberEvent
    data class OnGuess(val number: String) : GuessNumberEvent
    data object OnNewGame : GuessNumberEvent
    data object OnSurrender : GuessNumberEvent
}
