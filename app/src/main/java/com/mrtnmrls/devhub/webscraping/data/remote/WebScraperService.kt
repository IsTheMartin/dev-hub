package com.mrtnmrls.devhub.webscraping.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class WebScraperService {
    suspend fun scrapeWebpage(url: String): Result<Document> = withContext(Dispatchers.IO) {
        try {
            val document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .get()
            Result.success(document)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}