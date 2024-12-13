package com.mrtnmrls.devhub.domain.usecase

import android.util.Patterns
import com.mrtnmrls.devhub.domain.UseCaseResult
import javax.inject.Inject

class EmailValidatorUseCase @Inject constructor() {

    operator fun invoke(email: String): UseCaseResult<Unit> {
        return when {
            email.isBlank() -> UseCaseResult.Failed(Error("Email cannot be empty"))
            !Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> UseCaseResult.Failed(Error("Invalid email format"))

            else -> UseCaseResult.Success(Unit)
        }
    }

}
