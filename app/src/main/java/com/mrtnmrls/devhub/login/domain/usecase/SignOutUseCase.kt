package com.mrtnmrls.devhub.login.domain.usecase

import com.mrtnmrls.devhub.login.data.repository.AuthenticationRepository
import com.mrtnmrls.devhub.common.domain.UseCaseResult
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(): UseCaseResult<Boolean> {
        return repository.signOut()
    }

}
