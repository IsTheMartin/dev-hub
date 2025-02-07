package com.mrtnmrls.devhub.login.presentation

sealed class LoginEffect {
    data object NoOp : LoginEffect()
    data object OnSuccessfulLogin : LoginEffect()
}
