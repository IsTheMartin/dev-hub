package com.mrtnmrls.devhub.login.data.repository

import com.mrtnmrls.devhub.common.domain.UseCaseResult
import com.mrtnmrls.devhub.login.domain.model.CurrentUser

interface AuthenticationRepository {

    suspend fun isUserLogged(): UseCaseResult<Boolean>

    suspend fun emailLogin(email: String, password: String): UseCaseResult<Unit>

    suspend fun signOut(): UseCaseResult<Boolean>

    suspend fun getUserLoggedInformation(): UseCaseResult<CurrentUser>
}
