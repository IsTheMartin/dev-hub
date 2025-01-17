@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.mrtnmrls.devhub.pulltorefresh.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mrtnmrls.devhub.R
import com.mrtnmrls.devhub.common.ui.view.DevTopAppBar
import com.mrtnmrls.devhub.domain.model.NintendoSwitch
import com.mrtnmrls.devhub.common.ui.view.LoadingLottieView
import com.mrtnmrls.devhub.presentation.ui.theme.AzureishWhite
import com.mrtnmrls.devhub.pulltorefresh.presentation.PullToRefreshIntent
import com.mrtnmrls.devhub.pulltorefresh.presentation.PullToRefreshScreenState
import com.mrtnmrls.devhub.pulltorefresh.presentation.PullToRefreshResiliencyState
import com.mrtnmrls.devhub.pulltorefresh.presentation.PullToRefreshViewModel

@Composable
internal fun PullToRefreshContainer(navController: NavHostController) {
    val pullToRefreshViewModel = hiltViewModel<PullToRefreshViewModel>()
    val state by pullToRefreshViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            DevTopAppBar(
                title = "Pull to refresh"
            ) { navController.navigateUp() }
        }
    ) { paddingValues ->
        PullToRefreshScreen(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onIntent = pullToRefreshViewModel::dispatchIntent
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PullToRefreshScreen(
    modifier: Modifier = Modifier,
    state: PullToRefreshResiliencyState,
    onIntent: (PullToRefreshIntent) -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState(20.dp)
    when (state.screen) {
        PullToRefreshScreenState.Error -> ErrorResiliencyView(
            modifier,
            pullRefreshState
        ) {
            onIntent(PullToRefreshIntent.OnPullToRefresh)
        }

        PullToRefreshScreenState.Loading -> {
            pullRefreshState.endRefresh()
            LoadingLottieView(modifier.background(AzureishWhite))
        }

        is PullToRefreshScreenState.SuccessContent -> ContentView(
            modifier,
            state.screen.nintendoGames,
            pullRefreshState,
        ) {
            onIntent(PullToRefreshIntent.OnPullToRefresh)
        }
    }
}

@Composable
private fun ErrorResiliencyView(
    modifier: Modifier = Modifier,
    pullRefreshState: PullToRefreshState,
    onRefresh: () -> Unit
) {
    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            onRefresh()
        }
    }

    Box(
        modifier = modifier
            .nestedScroll(pullRefreshState.nestedScrollConnection)
            .fillMaxSize()
            .background(AzureishWhite),
        contentAlignment = Alignment.Center
    ) {
        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(R.drawable.server_down), contentDescription = "")
            Spacer(modifier = Modifier.height(32.dp))
            Text("No internet connection")
        }
    }
}

@Composable
private fun ContentView(
    modifier: Modifier = Modifier,
    list: List<NintendoSwitch>,
    pullRefreshState: PullToRefreshState,
    onRefresh: () -> Unit
) {
    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            onRefresh()
        }
    }

    Box(
        modifier = modifier
            .nestedScroll(pullRefreshState.nestedScrollConnection)
            .fillMaxSize()
            .background(AzureishWhite),
        contentAlignment = Alignment.Center
    ) {
        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        if (list.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(R.drawable.empty_data), contentDescription = "")
                Spacer(modifier = Modifier.height(32.dp))
                Text("No internet connection")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
                    .nestedScroll(pullRefreshState.nestedScrollConnection)
            ) {
                items(list) {
                    Row {
                        Text(it.name)
                    }
                }
            }
        }
    }
}
