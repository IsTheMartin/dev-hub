package com.mrtnmrls.devhub.landing.presentation

sealed class LandingEffect {
    data object NoOp : LandingEffect()
    data object OnSuccessfulSignOut : LandingEffect()
}
