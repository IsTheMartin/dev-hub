package com.mrtnmrls.devhub.presentation.ui.intent

sealed class LandingIntent {

    data object OnSignOut : LandingIntent()
    data object OnProfileDialogShow : LandingIntent()
    data object OnProfileDialogDismiss : LandingIntent()

}
