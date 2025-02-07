package com.mrtnmrls.devhub.login.data.repository

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.mrtnmrls.devhub.common.domain.UseCaseResult
import com.mrtnmrls.devhub.login.domain.mapper.CurrentUserMapper
import com.mrtnmrls.devhub.login.domain.model.CurrentUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val currentUserMapper: CurrentUserMapper
) : AuthenticationRepository {

    override suspend fun isUserLogged(): UseCaseResult<Boolean> {
        return try {
            if(firebaseAuth.currentUser != null) {
                UseCaseResult.Success(true)
            } else {
                UseCaseResult.Success(false)
            }
        } catch (exception: FirebaseException) {
            UseCaseResult.Failed(exception)
        }
    }

    override suspend fun getUserLoggedInformation(): UseCaseResult<CurrentUser> {
        return try {
            if (firebaseAuth.currentUser != null) {
                UseCaseResult.Success(currentUserMapper.transform(firebaseAuth.currentUser!!))
            } else {
                UseCaseResult.Failed(Exception("Cannot find user information"))
            }
        } catch (exception: FirebaseException) {
            UseCaseResult.Failed(exception)
        }
    }

    override suspend fun emailLogin(email: String, password: String): UseCaseResult<Unit> {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password)
                .await()
            return if (result != null) {
                UseCaseResult.Success(Unit)
            } else {
                UseCaseResult.Failed(Exception("Cannot login"))
            }
        } catch(exception: FirebaseException) {
            return UseCaseResult.Failed(exception)
        }
    }

    override suspend fun signOut(): UseCaseResult<Boolean> {
        try {
            firebaseAuth.signOut()
            return UseCaseResult.Success(true)
        } catch (exception: FirebaseException) {
            return UseCaseResult.Failed(exception)
        }
    }
}
