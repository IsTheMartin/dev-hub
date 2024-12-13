package com.mrtnmrls.devhub.data.repository

import com.mrtnmrls.devhub.domain.model.Esp8266
import com.mrtnmrls.devhub.domain.UseCaseResult
import kotlinx.coroutines.flow.Flow

interface Esp8266Repository {

    suspend fun fetchEsp8266Data(): Flow<UseCaseResult<Esp8266>>

    suspend fun toggleChristmasLights(newValue: Boolean): UseCaseResult<Unit>

}
