package com.mrtnmrls.devhub.login.domain.usecase

import com.mrtnmrls.devhub.common.domain.UseCaseResult
import javax.inject.Inject

class PasswordValidatorUseCase @Inject constructor() {

    operator fun invoke(password: String): UseCaseResult<Unit> {
        return when {
            password.isBlank() -> UseCaseResult.Failed(Error("Password cannot be empty"))
            password.length < 6 -> UseCaseResult.Failed(Error("Password must be at least 6 characters"))
            password.length > 20 -> UseCaseResult.Failed(Error("Password must be less than 20 characters"))
            else -> UseCaseResult.Success(Unit)
        }
    }

}
