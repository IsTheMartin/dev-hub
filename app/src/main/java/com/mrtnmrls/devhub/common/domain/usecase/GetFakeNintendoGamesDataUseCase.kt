package com.mrtnmrls.devhub.common.domain.usecase

import com.mrtnmrls.devhub.common.data.repository.MockRepository
import com.mrtnmrls.devhub.common.domain.UseCaseResult
import com.mrtnmrls.devhub.common.domain.model.NintendoSwitch
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
