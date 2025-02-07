package com.mrtnmrls.devhub.landing.presentation

sealed class LandingIntent {

    data object OnSignOut : LandingIntent()
    data object OnProfileDialogShow : LandingIntent()
    data object OnProfileDialogDismiss : LandingIntent()

}
