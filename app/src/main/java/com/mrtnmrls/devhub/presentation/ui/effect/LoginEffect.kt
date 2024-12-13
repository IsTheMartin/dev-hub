package com.mrtnmrls.devhub.presentation.ui.effect

sealed class LoginEffect {
    data object NoOp : LoginEffect()
    data object OnSuccessfulLogin : LoginEffect()
}
