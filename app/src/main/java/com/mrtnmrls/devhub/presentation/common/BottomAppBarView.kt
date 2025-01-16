package com.mrtnmrls.devhub.presentation.common

import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrtnmrls.devhub.presentation.ui.theme.CetaceanBlue
import com.mrtnmrls.devhub.presentation.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.presentation.ui.theme.Khaki
import com.mrtnmrls.devhub.presentation.ui.theme.MetallicBlue

@Composable
internal fun BottomAppBarView(modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier.height(50.dp),
        contentColor = CetaceanBlue,
        containerColor = MetallicBlue
    ) {
        Text(text = "Made by Martin with 🧠", color = Khaki)
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
