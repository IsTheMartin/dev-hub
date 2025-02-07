package com.mrtnmrls.devhub.esp8266.domain.usecase

import com.mrtnmrls.devhub.esp8266.domain.model.Esp8266
import com.mrtnmrls.devhub.esp8266.data.repository.Esp8266Repository
import com.mrtnmrls.devhub.common.domain.UseCaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEsp8266Data @Inject constructor(
    private val repository: Esp8266Repository
) {

    suspend operator fun invoke(): Flow<UseCaseResult<Esp8266>> {
        return repository.fetchEsp8266Data()
    }

}
