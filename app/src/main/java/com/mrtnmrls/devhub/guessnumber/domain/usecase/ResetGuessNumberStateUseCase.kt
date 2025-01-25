package com.mrtnmrls.devhub.guessnumber.domain.usecase

import com.mrtnmrls.devhub.guessnumber.domain.repository.GuessNumberRepository
import javax.inject.Inject

class ResetGuessNumberStateUseCase @Inject constructor(
    private val guessNumberRepository: GuessNumberRepository
) {
    operator fun invoke() = guessNumberRepository.resetGameState()
}

