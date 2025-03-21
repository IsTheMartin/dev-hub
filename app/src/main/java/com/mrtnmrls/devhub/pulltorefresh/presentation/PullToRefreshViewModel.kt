package com.mrtnmrls.devhub.pulltorefresh.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrtnmrls.devhub.common.domain.UseCaseResult
import com.mrtnmrls.devhub.common.domain.usecase.GetFakeNintendoGamesDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class PullToRefreshViewModel @Inject constructor(
    private val getFakeNintendoGamesDataUseCase: GetFakeNintendoGamesDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PullToRefreshResiliencyState())
    val state = _state
        .asStateFlow()
        .onStart { loadContent() }
        .stateIn(viewModelScope, started = SharingStarted.Lazily, PullToRefreshResiliencyState())

    internal fun dispatchIntent(intent: PullToRefreshIntent) {
        when (intent) {
            PullToRefreshIntent.OnPullToRefresh,
            PullToRefreshIntent.OnRefreshContent -> loadContent()
        }
    }

    private fun loadContent() = viewModelScope.launch {
        _state.update { it.copy(screen = PullToRefreshScreenState.Loading) }
        delay(3.seconds)
        when (val result = getFakeNintendoGamesDataUseCase()) {
            is UseCaseResult.Failed -> _state.update { it.copy(screen = PullToRefreshScreenState.Error) }
            is UseCaseResult.Success -> _state.update {
                it.copy(
                    screen = PullToRefreshScreenState.SuccessContent(
                        result.value
                    )
                )
            }
        }

    }
}
