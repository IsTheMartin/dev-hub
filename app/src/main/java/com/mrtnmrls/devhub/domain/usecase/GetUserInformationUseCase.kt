package com.mrtnmrls.devhub.domain.usecase

import com.mrtnmrls.devhub.data.repository.AuthenticationRepository
import com.mrtnmrls.devhub.domain.UseCaseResult
import com.mrtnmrls.devhub.domain.model.CurrentUser
import javax.inject.Inject

class GetUserInformationUseCase @Inject constructor(
    private val repository: AuthenticationRepository
){

    suspend operator fun invoke(): UseCaseResult<CurrentUser> {
        return repository.getUserLoggedInformation()
    }

}