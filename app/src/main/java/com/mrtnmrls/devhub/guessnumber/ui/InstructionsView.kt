package com.mrtnmrls.devhub.guessnumber.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrtnmrls.devhub.R
import com.mrtnmrls.devhub.common.ui.view.HorizontalSpacer
import com.mrtnmrls.devhub.common.ui.view.VerticalSpacer
import com.mrtnmrls.devhub.guessnumber.presentation.GuessNumberEvent
import com.mrtnmrls.devhub.common.ui.theme.AzureishWhite
import com.mrtnmrls.devhub.common.ui.theme.CadetBlue
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.common.ui.theme.JapaneseIndigo
import com.mrtnmrls.devhub.common.ui.theme.Typography

@Composable
fun InstructionsView(
    modifier: Modifier = Modifier,
    onEvent: (GuessNumberEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(CadetBlue)
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Instructions",
                    tint = AzureishWhite
                )
                HorizontalSpacer(8.dp)
                Text(
                    text = stringResource(R.string.guess_number_instructions),
                    color = JapaneseIndigo,
                    style = Typography.bodyMedium
                )
            }
        }
        VerticalSpacer(16.dp)
        StartNewGameButton(
            onEvent = onEvent
        )
    }
}

@Preview
@Composable
private fun PreviewInstructionsView() {
    DevhubTheme {
        Surface {
            InstructionsView { }
        }
    }
}
