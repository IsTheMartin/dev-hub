package com.mrtnmrls.devhub.presentation.ui.intent

sealed class PullToRefreshIntent {
    data object OnPullToRefresh : PullToRefreshIntent()
    data object OnRefreshContent : PullToRefreshIntent()
}
