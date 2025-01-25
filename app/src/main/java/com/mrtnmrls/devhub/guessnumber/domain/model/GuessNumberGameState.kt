package com.mrtnmrls.devhub.guessnumber.domain.model

data class GuessNumberGameState (
    val targetNumber: Int = 0,
    val feedback: String = "",
    val attempts: Int = 0,
    val isWinner: Boolean = false
)
