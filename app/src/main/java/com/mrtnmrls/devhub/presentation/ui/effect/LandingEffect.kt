package com.mrtnmrls.devhub.presentation.ui.effect

sealed class LandingEffect {
    data object NoOp : LandingEffect()
    data object OnSuccessfulSignOut : LandingEffect()
}
