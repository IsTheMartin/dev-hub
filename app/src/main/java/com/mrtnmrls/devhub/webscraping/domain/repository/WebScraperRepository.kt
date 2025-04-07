package com.mrtnmrls.devhub.webscraping.domain.repository

import com.mrtnmrls.devhub.common.domain.UseCaseResult
import com.mrtnmrls.devhub.webscraping.domain.model.ScrapedData

interface WebScraperRepository {
    suspend fun scrapeWebsite(url: String): UseCaseResult<List<ScrapedData>>
    suspend fun scrapeNewsArticle(url: String): UseCaseResult<List<ScrapedData>>
    suspend fun scrapeEcommerceProduct(url: String): UseCaseResult<List<ScrapedData>>
}