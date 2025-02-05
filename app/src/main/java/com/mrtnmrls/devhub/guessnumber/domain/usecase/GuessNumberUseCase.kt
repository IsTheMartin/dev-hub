package com.mrtnmrls.devhub.guessnumber.domain.usecase

import com.mrtnmrls.devhub.R
import com.mrtnmrls.devhub.common.domain.repository.ResourceProvider
import com.mrtnmrls.devhub.guessnumber.domain.model.GuessNumberGameState
import javax.inject.Inject

class GuessNumberUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    operator fun invoke(
        gameState: GuessNumberGameState,
        guessNumber: Int
    ): GuessNumberGameState {
        val newHistory = gameState.history.toMutableList().apply {
            add(guessNumber.toString())
        }
        val newState = gameState.copy(
            attempts = gameState.attempts + 1,
            history = newHistory
        )
        return when {
            guessNumber < gameState.targetNumber -> newState.copy(
                feedback = resourceProvider.getString(R.string.guess_number_too_low)
            )

            guessNumber > gameState.targetNumber -> newState.copy(
                feedback = resourceProvider.getString(R.string.guess_number_too_high)
            )

            else -> newState.copy(
                isWinner = true
            )
        }
    }
}
