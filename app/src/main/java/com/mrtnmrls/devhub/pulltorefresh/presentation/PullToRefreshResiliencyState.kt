package com.mrtnmrls.devhub.pulltorefresh.presentation

import com.mrtnmrls.devhub.domain.model.NintendoSwitch

data class PullToRefreshResiliencyState(
    val screen: PullToRefreshScreenState = PullToRefreshScreenState.Loading,
)

sealed class PullToRefreshScreenState {
    data object Loading : PullToRefreshScreenState()
    data object Error: PullToRefreshScreenState()
    data class SuccessContent(
        val nintendoGames: List<NintendoSwitch>
    ) : PullToRefreshScreenState()
}
