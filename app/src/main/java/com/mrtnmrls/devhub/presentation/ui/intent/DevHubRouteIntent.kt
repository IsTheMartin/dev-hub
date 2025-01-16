package com.mrtnmrls.devhub.presentation.ui.intent

sealed interface DevHubRouteIntent {
    data object OnPullToRefreshClicked : DevHubRouteIntent
    data object OnESP8266Clicked : DevHubRouteIntent
    data object OnTodoListClicked : DevHubRouteIntent
}
