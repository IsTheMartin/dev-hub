package com.mrtnmrls.devhub.guessnumber.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.mrtnmrls.devhub.R
import com.mrtnmrls.devhub.common.ui.view.PrimaryButton
import com.mrtnmrls.devhub.common.ui.view.SecondaryButton
import com.mrtnmrls.devhub.common.ui.view.VerticalSpacer
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberEvent
import com.mrtnmrls.devhub.common.ui.theme.AzureishWhite
import com.mrtnmrls.devhub.common.ui.theme.CadetBlue
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.common.ui.theme.JapaneseIndigo
import com.mrtnmrls.devhub.common.ui.theme.Typography

@Composable
internal fun GameInProgressView(
    modifier: Modifier = Modifier,
    attempts: Int,
    feedback: String,
    history: List<String>,
    onEvent: (GuessNumberEvent) -> Unit
) {
    var number by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    LaunchedEffect(key1 = history.size) {
        if (history.isNotEmpty()) {
            lazyListState.scrollToItem(history.size - 1)
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.guess_number_current_attempts, attempts),
            color = JapaneseIndigo,
            style = Typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        VerticalSpacer(12.dp)
        InputField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            number = number,
            onValueChange = {
                number = it
            },
            onDone = {
                onEvent(GuessNumberEvent.OnGuess(number))
                number = ""
            }
        )
        VerticalSpacer(12.dp)
        Text(
            text = feedback,
            color = JapaneseIndigo
        )
        VerticalSpacer(12.dp)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(CadetBlue)
                .height(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            reverseLayout = true,
            state = lazyListState
        ) {
            itemsIndexed(history) { index, historyItem ->
                Text(
                    text = historyItem,
                    fontWeight = if (index == history.size - 1) FontWeight.Black else FontWeight.Normal
                )
            }
        }
        VerticalSpacer(12.dp)
        PrimaryButton(
            modifier = modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(GuessNumberEvent.OnGuess(number))
                number = ""
            },
            buttonText = stringResource(R.string.guess_number_guess)
        )
        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(GuessNumberEvent.OnSurrender)
            },
            buttonText = stringResource(R.string.guess_number_surrender)
        )
    }
}

@Composable
private fun InputField(
    modifier: Modifier = Modifier,
    number: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = number,
        onValueChange = {
            if (it.isDigitsOnly()) {
                onValueChange(it)
            }
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            showKeyboardOnFocus = true
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = AzureishWhite,
            focusedTextColor = JapaneseIndigo,
            focusedLabelColor = JapaneseIndigo,
            unfocusedContainerColor = AzureishWhite,
            unfocusedTextColor = JapaneseIndigo,
            unfocusedLabelColor = JapaneseIndigo,
            cursorColor = JapaneseIndigo,
            focusedBorderColor = JapaneseIndigo
        ),
    )
}

@Preview
@Composable
private fun PreviewGameInProgressView() {
    DevhubTheme {
        Surface {
            GameInProgressView(
                attempts = 5,
                feedback = stringResource(R.string.guess_number_too_low),
                history = listOf("53", "32", "48", "43", "40", "53", "32", "48", "43", "40")
            ) { }
        }
    }
}
