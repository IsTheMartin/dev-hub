package com.mrtnmrls.devhub.common.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme

@Composable
internal fun BottomAppBarView(modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier
            .height(60.dp),
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        val annotatedString = buildAnnotatedString {
            append("Made by ")
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Black
                )
            ) {
                append("Martín ")
            }
            append("with \uD83E\uDDE0")
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = annotatedString
        )
    }
}

@Preview
@Composable
private fun PreviewBottomAppBarView() {
    DevhubTheme {
        Surface {
            BottomAppBarView()
        }
    }
}
