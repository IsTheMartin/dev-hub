package com.mrtnmrls.devhub.guessnumber.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrtnmrls.devhub.R
import com.mrtnmrls.devhub.common.ui.view.VerticalSpacer
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberEvent
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.common.ui.theme.Typography

@Composable
fun GameOverView(
    modifier: Modifier = Modifier,
    targetNumber: Int,
    attempts: Int,
    onEvent: (GuessNumberEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = targetNumber.toString(),
            style = Typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        VerticalSpacer(8.dp)
        Text(
            text = stringResource(R.string.guess_number_game_over),
            color = MaterialTheme.colorScheme.primary,
            style = Typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "You lost in $attempts attempts",
            color = MaterialTheme.colorScheme.secondary,
            style = Typography.bodyMedium
        )
        VerticalSpacer(32.dp)
        StartNewGameButton(
            onEvent = onEvent
        )
    }
}

@Preview
@Composable
private fun PreviewGameOverView() {
    DevhubTheme {
        Surface {
            GameOverView(
                targetNumber = 75,
                attempts = 10
            ) { }
        }
    }
}
