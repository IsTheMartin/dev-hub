package com.mrtnmrls.devhub.presentation.ui.intent

sealed class LoginIntent {
    data class OnLogin(val email: String, val password: String) : LoginIntent()
    data object OnContinueWithoutLogin : LoginIntent()
}
