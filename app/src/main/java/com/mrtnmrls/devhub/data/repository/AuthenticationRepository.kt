package com.mrtnmrls.devhub.data.repository

import com.mrtnmrls.devhub.domain.UseCaseResult
import com.mrtnmrls.devhub.domain.model.CurrentUser

interface AuthenticationRepository {

    suspend fun isUserLogged(): UseCaseResult<Boolean>

    suspend fun emailLogin(email: String, password: String): UseCaseResult<Unit>

    suspend fun signOut(): UseCaseResult<Boolean>

    suspend fun getUserLoggedInformation(): UseCaseResult<CurrentUser>
}
