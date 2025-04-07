package com.mrtnmrls.devhub.webscraping.presentation.viewmodel

import com.mrtnmrls.devhub.webscraping.domain.model.ScrapedData

data class WebScraperState(
    val uiState: WebScraperUiState = WebScraperUiState.Start
)

sealed interface WebScraperUiState {
    data object Start : WebScraperUiState
    data object Loading : WebScraperUiState
    data class ContentSuccess(val data: List<ScrapedData>) : WebScraperUiState
    data object Error : WebScraperUiState
}