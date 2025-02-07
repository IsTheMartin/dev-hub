package com.mrtnmrls.devhub.login.domain.usecase

import com.mrtnmrls.devhub.login.data.repository.AuthenticationRepository
import com.mrtnmrls.devhub.common.domain.UseCaseResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val passwordValidatorUseCase: PasswordValidatorUseCase
) {

    suspend operator fun invoke(email: String, password: String): UseCaseResult<Unit> {
        val emailValidation = emailValidatorUseCase(email)
        val passwordValidation = passwordValidatorUseCase(password)
        if (emailValidation is UseCaseResult.Failed) {
            return emailValidation
        }
        if (passwordValidation is UseCaseResult.Failed) {
            return passwordValidation
        }
        return repository.emailLogin(email, password)
    }

}
