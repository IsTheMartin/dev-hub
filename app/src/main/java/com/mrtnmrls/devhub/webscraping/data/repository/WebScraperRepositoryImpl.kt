package com.mrtnmrls.devhub.webscraping.data.repository

import com.mrtnmrls.devhub.common.domain.UseCaseResult
import com.mrtnmrls.devhub.webscraping.data.remote.WebScraperService
import com.mrtnmrls.devhub.webscraping.domain.model.ScrapedData
import com.mrtnmrls.devhub.webscraping.domain.repository.WebScraperRepository
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import javax.inject.Inject

class WebScraperRepositoryImpl @Inject constructor(
    private val webScraperService: WebScraperService
) : WebScraperRepository{
    override suspend fun scrapeWebsite(url: String): UseCaseResult<List<ScrapedData>> {
        val documentResult = webScraperService.scrapeWebpage(url)

        return documentResult.fold(
            onSuccess = { document ->
                val scrapedData = scrapeGenericContent(document, url)
                UseCaseResult.Success(listOf(scrapedData))
            },
            onFailure = { error ->
                UseCaseResult.Failed(error)
            }
        )
    }

    private fun scrapeGenericContent(document: Document, url: String): ScrapedData {
        // Extraer el título de la página
        val title = document.title() ?: document.select("h1").firstOrNull()?.text() ?: "Sin título"

        // Extraer metadatos
        val metaDescription = document.select("meta[name=description]").attr("content")
        val metaKeywords = document.select("meta[name=keywords]").attr("content")
        val metaAuthor = document.select("meta[name=author]").attr("content")

        // Detectar y extraer el contenido principal
        val mainContent = findMainContent(document)

        // Extraer imágenes relevantes
        val mainImage = findMainImage(document)

        // Crear una descripción enriquecida
        val enrichedDescription = buildString {
            if (metaDescription.isNotEmpty()) {
                append("$metaDescription\n\n")
            }

            if (metaKeywords.isNotEmpty()) {
                append("Palabras clave: $metaKeywords\n\n")
            }

            if (metaAuthor.isNotEmpty()) {
                append("Autor: $metaAuthor\n\n")
            }

            append("Contenido:\n$mainContent")
        }

        return ScrapedData(
            title = title,
            description = enrichedDescription,
            imageUrl = mainImage,
            link = url
        )
    }

    private fun findMainContent(document: Document): String {
        // Lista de selectores para encontrar el contenido principal en diferentes sitios web
        val contentSelectors = listOf(
            // Selectores comunes para artículos y blogs
            "article", ".post-content", ".entry-content", ".article-content",
            ".content-area", "#content", ".main-content", "main",

            // Selectores para sitios de noticias
            ".story-body", ".news-article", ".article-body",

            // Selectores para páginas de productos
            ".product-description", "#product-description", ".description",

            // Contenedores genéricos con mayor probabilidad de contener el contenido principal
            ".container", ".wrapper"
        )

        // Intentar encontrar el elemento de contenido principal
        for (selector in contentSelectors) {
            val element = document.select(selector).firstOrNull()
            if (element != null && element.text().length > 150) {
                // Limpiar el contenido obtenido
                return cleanContent(element)
            }
        }

        // Si no se encuentra un contenedor específico, extraer párrafos relevantes
        val paragraphs = document.select("p")
        if (paragraphs.isNotEmpty()) {
            // Filtrar párrafos con contenido significativo (más de 100 caracteres)
            val significantParagraphs = paragraphs
                // .filter { it.text().length > 100 }
                .joinToString("\n\n") { it.text() }

            if (significantParagraphs.isNotEmpty()) {
                return significantParagraphs
            }
        }

        // Si todo falla, extraer el texto del body, pero limitado a los primeros 1000 caracteres
        val bodyText = document.body().text()
        return if (bodyText.length > 1000) {
            bodyText.substring(0, 1000) + "..."
        } else {
            bodyText
        }
    }

    private fun cleanContent(element: Element): String {
        // Eliminar elementos no deseados
        element.select("script, style, iframe, .advertisement, .ads, .comments, .comment-section, .sidebar, nav, footer, header").remove()

        // Construir el contenido con formato
        val builder = StringBuilder()

        // Procesar encabezados
        for (i in 1..6) {
            element.select("h$i").forEach { heading ->
                builder.append("\n${heading.text()}\n")
            }
        }

        // Procesar párrafos
        element.select("p").forEach { paragraph ->
            val text = paragraph.text().trim()
            if (text.isNotEmpty()) {
                builder.append("$text\n\n")
            }
        }

        // Procesar listas
        element.select("ul, ol").forEach { list ->
            list.select("li").forEach { item ->
                builder.append("• ${item.text()}\n")
            }
            builder.append("\n")
        }

        // Si no se ha agregado suficiente contenido, usar todo el texto
        if (builder.length < 150) {
            return element.text()
        }

        return builder.toString().trim()
    }

    private fun findMainImage(document: Document): String? {
        // Lista de selectores para encontrar la imagen principal
        val imageSelectors = listOf(
            // Imágenes destacadas
            ".featured-image img", ".post-thumbnail img", ".main-image img",

            // Imágenes OG para redes sociales (suelen ser representativas)
            "meta[property=og:image]",

            // Imágenes de productos
            ".product-image img", "#product-image img",

            // Imágenes hero o de cabecera
            ".hero img", ".header-image img",

            // Cualquier imagen dentro del contenido principal
            "article img", ".entry-content img", ".post-content img", ".content img"
        )

        // Buscar la imagen según los selectores
        for (selector in imageSelectors) {
            val element = document.select(selector).firstOrNull()
            if (element != null) {
                // Para meta tags
                if (selector.startsWith("meta")) {
                    val content = element.attr("content")
                    if (content.isNotEmpty()) return content
                }
                // Para elementos img
                else {
                    val src = element.attr("src")
                    if (src.isNotEmpty()) return src
                }
            }
        }

        // Si no se encuentra una imagen principal, buscar cualquier imagen relevante
        val images = document.select("img")

        // Filtrar imágenes pequeñas y de iconos
        return images
            .filter { img ->
                // Intentar obtener atributos de dimensiones
                val width = img.attr("width").toIntOrNull() ?: 0
                val height = img.attr("height").toIntOrNull() ?: 0

                // Filtrar iconos y imágenes muy pequeñas
                (width == 0 || width > 100) && (height == 0 || height > 100) &&
                        !img.attr("src").contains("icon", ignoreCase = true) &&
                        !img.attr("src").contains("logo", ignoreCase = true) &&
                        img.attr("src").isNotEmpty()
            }
            .firstOrNull()?.attr("src")
    }

    // Método para detectar si el URL es de un artículo de noticias
    override suspend fun scrapeNewsArticle(url: String): UseCaseResult<List<ScrapedData>> {
        val documentResult = webScraperService.scrapeWebpage(url)

        return documentResult.fold(
            onSuccess = { document ->
                try {
                    // Extraer título
                    val title = document.select(".article-title, .entry-title, h1").firstOrNull()?.text()
                        ?: document.title()

                    // Extraer autor
                    val author = document.select(".author, .byline, .meta-author").text()

                    // Extraer fecha
                    val date = document.select(".published-date, .post-date, time").attr("datetime")
                        .ifEmpty { document.select(".published-date, .post-date, time").text() }

                    // Extraer contenido del artículo
                    val content = document.select(".article-body, .entry-content, .post-content").text()

                    // Extraer imagen principal
                    val imageUrl = document.select(".featured-image img, .post-thumbnail img").attr("src")
                        .ifEmpty { document.select("meta[property=og:image]").attr("content") }

                    // Construir descripción
                    val description = buildString {
                        if (author.isNotEmpty()) append("Por: $author\n")
                        if (date.isNotEmpty()) append("Fecha: $date\n\n")
                        append(content)
                    }

                    val scrapedData = ScrapedData(
                        title = title,
                        description = description,
                        imageUrl = imageUrl.ifEmpty { null },
                        link = url
                    )

                    UseCaseResult.Success(listOf(scrapedData))
                } catch (e: Exception) {
                    UseCaseResult.Failed(Exception("Error al procesar el artículo: ${e.message}"))
                }
            },
            onFailure = { error ->
                UseCaseResult.Failed(error)
            }
        )
    }

    // Método específico para detectar y extraer datos de e-commerce
    override suspend fun scrapeEcommerceProduct(url: String): UseCaseResult<List<ScrapedData>> {
        val documentResult = webScraperService.scrapeWebpage(url)

        return documentResult.fold(
            onSuccess = { document ->
                try {
                    // Detectar la plataforma e-commerce
                    val platform = when {
                        url.contains("amazon", ignoreCase = true) -> "Amazon"
                        url.contains("ebay", ignoreCase = true) -> "eBay"
                        url.contains("walmart", ignoreCase = true) -> "Walmart"
                        url.contains("aliexpress", ignoreCase = true) -> "AliExpress"
                        url.contains("etsy", ignoreCase = true) -> "Etsy"
                        else -> "Desconocido"
                    }

                    // Adaptar los selectores según la plataforma
                    val (title, price, description, imageUrl, rating) = when (platform) {
                        "Amazon" -> extractAmazonProductDetails(document)
                        "eBay" -> extractEbayProductDetails(document)
                        else -> extractGenericProductDetails(document)
                    }

                    val formattedDescription = buildString {
                        append("Plataforma: $platform\n")
                        append("Precio: $price\n")
                        if (rating.isNotEmpty()) append("Valoración: $rating\n\n")
                        append("Descripción del producto:\n$description")
                    }

                    val scrapedData = ScrapedData(
                        title = title,
                        description = formattedDescription,
                        imageUrl = imageUrl,
                        link = url
                    )

                    UseCaseResult.Success(listOf(scrapedData))
                } catch (e: Exception) {
                    UseCaseResult.Failed(Exception("Error al procesar el producto: ${e.message}"))
                }
            },
            onFailure = { error ->
                UseCaseResult.Failed(error)
            }
        )
    }

    private fun extractAmazonProductDetails(document: Document): ProductDetails {
        val title = document.select("#productTitle").text().trim()

        val price = document.select(".a-price .a-offscreen").firstOrNull()?.text() ?:
        document.select("#priceblock_ourprice").text() ?:
        "Precio no disponible"

        val description = document.select("#feature-bullets .a-list-item").text() ?:
        document.select("#productDescription p").text() ?:
        "Sin descripción disponible"

        val imageUrl = document.select("#imgTagWrapperId img, #landingImage").firstOrNull()?.let { img ->
            img.attr("data-old-hires").ifEmpty { img.attr("src") }
        }

        val rating = document.select("#acrPopover .a-icon-alt").text()

        return ProductDetails(
            title = title,
            price = price,
            description = description,
            imageUrl = imageUrl,
            rating = rating
        )
    }

    private fun extractEbayProductDetails(document: Document): ProductDetails {
        val title = document.select("h1.x-item-title__mainTitle").text()
        val price = document.select(".x-price-primary").text()
        val description = document.select(".x-item-description").text()
        val imageUrl = document.select(".ux-image-carousel-item img").attr("src")
        val rating = document.select(".ux-seller-section__item--seller-rating").text()

        return ProductDetails(
            title = title,
            price = price,
            description = description,
            imageUrl = imageUrl,
            rating = rating
        )
    }

    private fun extractGenericProductDetails(document: Document): ProductDetails {
        // Intentar extraer título del producto
        val title = document.select(".product-title, .product-name, h1").firstOrNull()?.text()
            ?: document.title()

        // Intentar extraer precio
        val price = document.select(".price, .product-price, .offer-price").text()

        // Intentar extraer descripción
        val description = document.select(".product-description, .description, .details").text()
            .ifEmpty { document.select("p")
                //.filter { it.text().length > 100 }
                .joinToString("\n") { it.text() } }

        // Intentar extraer imagen
        val imageUrl = document.select(".product-image img, .main-image img").attr("src")
            .ifEmpty { document.select("img").firstOrNull { it.hasAttr("src") && !it.attr("src").contains("logo") }?.attr("src") }

        // Intentar extraer valoración
        val rating = document.select(".rating, .stars, .product-rating").text()

        return ProductDetails(
            title = title,
            price = price,
            description = description,
            imageUrl = imageUrl,
            rating = rating
        )
    }

    private data class ProductDetails(
        val title: String = "",
        val price: String = "",
        val description: String = "",
        val imageUrl: String? = null,
        val rating: String = ""
    )
}