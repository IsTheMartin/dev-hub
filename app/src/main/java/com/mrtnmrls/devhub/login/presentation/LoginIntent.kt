package com.mrtnmrls.devhub.login.presentation

sealed class LoginIntent {
    data class OnLogin(val email: String, val password: String) : LoginIntent()
    data object OnContinueWithoutLogin : LoginIntent()
}
