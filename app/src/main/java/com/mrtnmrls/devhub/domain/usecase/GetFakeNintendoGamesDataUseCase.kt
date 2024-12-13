package com.mrtnmrls.devhub.domain.usecase

import com.mrtnmrls.devhub.data.MockRepository
import com.mrtnmrls.devhub.domain.UseCaseResult
import com.mrtnmrls.devhub.domain.model.NintendoSwitch
import javax.inject.Inject

class GetFakeNintendoGamesDataUseCase @Inject constructor(
    private val mockRepository: MockRepository
)  {
    suspend operator fun invoke(): UseCaseResult<List<NintendoSwitch>> {
        val randomNumber = (0..10).random()
        return when (randomNumber) {
            in 0..3 -> UseCaseResult.Success(mockRepository.getNintendoGames())
            in 4..7 -> UseCaseResult.Success(emptyList())
            else -> UseCaseResult.Failed(Throwable("Something went wrong"))
        }
    }
}
