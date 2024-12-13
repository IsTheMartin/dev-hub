package com.mrtnmrls.devhub.presentation.ui.state

import com.mrtnmrls.devhub.presentation.ui.effect.LoginEffect

data class LoginState(
    val screenState: LoginScreenState = LoginScreenState.Loading,
    val effect: LoginEffect = LoginEffect.NoOp
)

sealed class LoginScreenState {
    data object Loading : LoginScreenState()
    data object Error : LoginScreenState()
    data class SuccessContent(
        val isLogIn: Boolean = false,
        val isMissingEmail: Boolean = false,
        val isMissingPassword: Boolean = false
    ) : LoginScreenState()
}
