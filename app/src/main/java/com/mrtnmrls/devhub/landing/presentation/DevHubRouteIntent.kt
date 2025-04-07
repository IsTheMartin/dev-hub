package com.mrtnmrls.devhub.landing.presentation

sealed interface DevHubRouteIntent {
    data object OnPullToRefreshClicked : DevHubRouteIntent
    data object OnESP8266Clicked : DevHubRouteIntent
    data object OnTodoListClicked : DevHubRouteIntent
    data object OnGuessNumberClicked : DevHubRouteIntent
    data object OnLazyMindMapClicked : DevHubRouteIntent
    data object OnWebScraperClicked : DevHubRouteIntent
}
