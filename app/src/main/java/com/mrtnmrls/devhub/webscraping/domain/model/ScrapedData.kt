package com.mrtnmrls.devhub.webscraping.domain.model

data class ScrapedData(
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val link: String? = null
)
