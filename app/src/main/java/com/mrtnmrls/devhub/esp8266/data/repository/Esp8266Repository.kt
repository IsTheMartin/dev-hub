package com.mrtnmrls.devhub.esp8266.data.repository

import com.mrtnmrls.devhub.esp8266.domain.model.Esp8266
import com.mrtnmrls.devhub.common.domain.UseCaseResult
import kotlinx.coroutines.flow.Flow

interface Esp8266Repository {

    suspend fun fetchEsp8266Data(): Flow<UseCaseResult<Esp8266>>

    suspend fun toggleChristmasLights(newValue: Boolean): UseCaseResult<Unit>

}
