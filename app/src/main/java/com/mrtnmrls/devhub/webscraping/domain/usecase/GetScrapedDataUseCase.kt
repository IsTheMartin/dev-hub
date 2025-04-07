package com.mrtnmrls.devhub.webscraping.domain.usecase

import com.mrtnmrls.devhub.common.domain.UseCaseResult
import com.mrtnmrls.devhub.webscraping.domain.model.ScrapedData
import com.mrtnmrls.devhub.webscraping.domain.repository.WebScraperRepository
import java.net.URL
import javax.inject.Inject

class GetScrapedDataUseCase @Inject constructor(
    private val repository: WebScraperRepository
) {
    suspend operator fun invoke(url: String): UseCaseResult<List<ScrapedData>> {
        return when {
            isEcommerceSite(url) -> {
                repository.scrapeEcommerceProduct(url)
            }
            isNewsSite(url) -> {
                repository.scrapeNewsArticle(url)
            }
            else -> {
                // Scraping genérico para cualquier otro tipo de página
                repository.scrapeWebsite(url)
            }
        }
    }

    private fun isEcommerceSite(url: String): Boolean {
        val ecommercePatterns = listOf(
            "amazon", "ebay", "walmart", "aliexpress", "etsy",
            "bestbuy", "target", "newegg", "wayfair", "homedepot",
            "product", "item", "buy", "shop", "store", "cart"
        )

        return ecommercePatterns.any { pattern ->
            url.contains(pattern, ignoreCase = true)
        }
    }

    // Método para detectar si es un sitio de noticias
    private fun isNewsSite(url: String): Boolean {
        val newsPatterns = listOf(
            "news", "article", "blog", "post", "story",
            "cnn", "bbc", "nytimes", "reuters", "washingtonpost",
            "guardian", "huffpost", "medium", "telegraph"
        )

        return newsPatterns.any { pattern ->
            url.contains(pattern, ignoreCase = true)
        }
    }

    // Método para obtener el dominio base de una URL
    private fun extractDomain(url: String): String {
        return try {
            val parsedUrl = URL(url)
            parsedUrl.host
        } catch (e: Exception) {
            url
        }
    }
}