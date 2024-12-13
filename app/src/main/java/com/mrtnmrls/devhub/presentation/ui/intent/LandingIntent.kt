package com.mrtnmrls.devhub.presentation.ui.intent

sealed class LandingIntent {

    data object OnSignOut : LandingIntent()

}
