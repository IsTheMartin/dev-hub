package com.mrtnmrls.devhub.presentation.ui.intent

sealed class DevHubRouteIntent {
    data object OnPullToRefreshClicked : DevHubRouteIntent()
    data object OnESP8266Clicked : DevHubRouteIntent()
}
