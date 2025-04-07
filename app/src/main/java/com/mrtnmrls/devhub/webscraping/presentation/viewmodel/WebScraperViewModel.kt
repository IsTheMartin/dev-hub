package com.mrtnmrls.devhub.webscraping.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrtnmrls.devhub.webscraping.domain.usecase.GetScrapedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebScraperViewModel @Inject constructor(
    private val getScrapedDataUseCase: GetScrapedDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WebScraperState())
    val state = _state.asStateFlow()

    fun dispatchIntent(intent: WebScraperIntent) {
        when (intent) {
            is WebScraperIntent.OnScrapingWeb -> scrapeWebsite(intent.url)
            is WebScraperIntent.OnNewUrl -> _state.value = _state.value.copy(uiState = WebScraperUiState.Start)
        }
    }

    private fun scrapeWebsite(url: String) {
        _state.value = _state.value.copy(uiState = WebScraperUiState.Loading)

        viewModelScope.launch {
            getScrapedDataUseCase(url).fold(
                onSuccess = { data ->
                    _state.value = _state.value.copy(uiState = WebScraperUiState.ContentSuccess(data))
                },
                onFailed = {
                    _state.value = _state.value.copy(uiState = WebScraperUiState.Error)
                }
            )
        }
    }
}