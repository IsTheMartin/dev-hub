package com.mrtnmrls.devhub.esp8266.domain.usecase

import com.mrtnmrls.devhub.esp8266.data.repository.Esp8266Repository
import com.mrtnmrls.devhub.common.domain.UseCaseResult
import javax.inject.Inject

class ToggleChristmasLightsUseCase @Inject constructor(
    private val repository: Esp8266Repository
) {

    suspend operator fun invoke(newValue: Boolean): UseCaseResult<Unit> {
        return repository.toggleChristmasLights(!newValue)
    }

}
