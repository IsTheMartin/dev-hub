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
        return when {
            guessNumber < gameState.targetNumber -> gameState.copy(
                feedback = resourceProvider.getString(R.string.guess_number_too_low),
                attempts = gameState.attempts + 1
            )

            guessNumber > gameState.targetNumber -> gameState.copy(
                feedback = resourceProvider.getString(R.string.guess_number_too_high),
                attempts = gameState.attempts + 1
            )

            else -> gameState.copy(
                attempts = gameState.attempts + 1,
                isWinner = true
            )
        }
    }
}
