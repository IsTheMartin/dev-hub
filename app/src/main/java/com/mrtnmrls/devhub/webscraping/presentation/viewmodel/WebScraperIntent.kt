package com.mrtnmrls.devhub.webscraping.presentation.viewmodel

sealed interface WebScraperIntent {
    data class OnScrapingWeb(val url: String) : WebScraperIntent
    data object OnNewUrl : WebScraperIntent
}