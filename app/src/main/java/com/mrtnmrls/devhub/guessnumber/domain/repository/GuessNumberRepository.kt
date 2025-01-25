package com.mrtnmrls.devhub.guessnumber.domain.repository

import com.mrtnmrls.devhub.guessnumber.domain.model.GuessNumberGameState

interface GuessNumberRepository {
    fun generateTargetNumber(): Int
    fun resetGameState(): GuessNumberGameState
}
