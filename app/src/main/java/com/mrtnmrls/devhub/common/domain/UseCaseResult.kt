package com.mrtnmrls.devhub.common.domain

sealed class UseCaseResult<T> {
    data class Success<T>(val value: T) : UseCaseResult<T>()
    data class Failed<T>(val error: Throwable) : UseCaseResult<T>()

    fun fold(
        onSuccess: (T) -> Unit,
        onFailed: (Throwable) -> Unit = {}
    ) {
        when (this) {
            is Failed -> onFailed(error)
            is Success -> onSuccess(value)
        }
    }
}
