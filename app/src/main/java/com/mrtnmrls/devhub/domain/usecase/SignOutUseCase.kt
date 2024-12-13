package com.mrtnmrls.devhub.domain.usecase

import com.mrtnmrls.devhub.data.repository.AuthenticationRepository
import com.mrtnmrls.devhub.domain.UseCaseResult
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(): UseCaseResult<Boolean> {
        return repository.signOut()
    }

}
