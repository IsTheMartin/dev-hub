package com.mrtnmrls.devhub.webscraping.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mrtnmrls.devhub.common.ui.compositionlocal.LocalNavController
import com.mrtnmrls.devhub.common.ui.view.DevTopAppBar
import com.mrtnmrls.devhub.common.ui.view.LoadingLottieView
import com.mrtnmrls.devhub.common.ui.view.PrimaryButton
import com.mrtnmrls.devhub.webscraping.domain.model.ScrapedData
import com.mrtnmrls.devhub.webscraping.presentation.viewmodel.WebScraperIntent
import com.mrtnmrls.devhub.webscraping.presentation.viewmodel.WebScraperUiState
import com.mrtnmrls.devhub.webscraping.presentation.viewmodel.WebScraperViewModel

@Composable
internal fun WebScraperContainer() {
    val navController = LocalNavController.current
    val viewModel: WebScraperViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { DevTopAppBar(title = "Web Scraper") { navController.navigateUp() } },
        bottomBar = {
            if (state.uiState is WebScraperUiState.ContentSuccess) {
                BottomAppBar {
                    PrimaryButton(
                        buttonText = "New scrap"
                    ) {
                        viewModel.dispatchIntent(WebScraperIntent.OnNewUrl)
                    }
                }
            }
        }
    ) { paddingValues ->
        WebScraperScreen(
            modifier = Modifier.padding(paddingValues),
            uiState = state.uiState,
            onIntent = viewModel::dispatchIntent
        )
    }
}

@Composable
private fun WebScraperScreen(
    uiState: WebScraperUiState,
    modifier: Modifier = Modifier,
    onIntent: (WebScraperIntent) -> Unit
) {
    when (val state = uiState) {
        is WebScraperUiState.ContentSuccess -> {
            if (state.data.isNotEmpty()) {
                ScrapingWebResultView(modifier, state.data.first(), onIntent)
            }
        }

        WebScraperUiState.Error -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        )

        WebScraperUiState.Loading -> LoadingLottieView()
        is WebScraperUiState.Start -> StartScrapingWebView(onIntent = onIntent)
    }
}

@Composable
private fun StartScrapingWebView(
    modifier: Modifier = Modifier,
    onIntent: (WebScraperIntent) -> Unit
) {
    var url by remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier,
            value = url,
            onValueChange = {
                url = it
            },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                showKeyboardOnFocus = true
            ),
            keyboardActions = KeyboardActions(
                onDone = { onIntent(WebScraperIntent.OnScrapingWeb(url)) }
            )
        )
        PrimaryButton(
            buttonText = "Scrap!"
        ) {
            onIntent(WebScraperIntent.OnScrapingWeb(url))
        }
    }
}

@Composable
private fun ScrapingWebResultView(
    modifier: Modifier = Modifier,
    data: ScrapedData,
    onIntent: (WebScraperIntent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = data.title,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = data.description,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        /*data.imageUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = data.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }*/

        data.link?.let { link ->
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enlace: $link",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}