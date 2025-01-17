package com.mrtnmrls.devhub.pulltorefresh.presentation

sealed class PullToRefreshIntent {
    data object OnPullToRefresh : PullToRefreshIntent()
    data object OnRefreshContent : PullToRefreshIntent()
}
