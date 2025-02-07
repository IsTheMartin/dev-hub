package com.mrtnmrls.devhub.login.domain.usecase

import com.mrtnmrls.devhub.login.data.repository.AuthenticationRepository
import com.mrtnmrls.devhub.common.domain.UseCaseResult
import com.mrtnmrls.devhub.login.domain.model.CurrentUser
import javax.inject.Inject

class GetUserInformationUseCase @Inject constructor(
    private val repository: AuthenticationRepository
){
    suspend operator fun invoke(): UseCaseResult<CurrentUser> {
        return repository.getUserLoggedInformation()
    }
}
