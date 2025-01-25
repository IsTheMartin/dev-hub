package com.mrtnmrls.devhub.guessnumber.data.repository

import com.mrtnmrls.devhub.guessnumber.domain.model.GuessNumberGameState
import com.mrtnmrls.devhub.guessnumber.domain.repository.GuessNumberRepository
import kotlin.random.Random

class GuessNumberRepositoryImpl: GuessNumberRepository {
    override fun generateTargetNumber(): Int = Random.nextInt(1,101)

    override fun resetGameState() = GuessNumberGameState(
        targetNumber = generateTargetNumber(),
        feedback = "",
        attempts = 0,
        isWinner = false
    )
}
