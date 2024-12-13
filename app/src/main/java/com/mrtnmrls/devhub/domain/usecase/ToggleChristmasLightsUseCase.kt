package com.mrtnmrls.devhub.domain.usecase

import com.mrtnmrls.devhub.data.repository.Esp8266Repository
import com.mrtnmrls.devhub.domain.UseCaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToggleChristmasLightsUseCase @Inject constructor(
    private val repository: Esp8266Repository
) {

    suspend operator fun invoke(newValue: Boolean): UseCaseResult<Unit> {
        return repository.toggleChristmasLights(!newValue)
    }

}
