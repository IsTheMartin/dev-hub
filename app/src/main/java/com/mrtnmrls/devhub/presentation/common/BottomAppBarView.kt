package com.mrtnmrls.devhub.presentation.common

import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrtnmrls.devhub.presentation.ui.theme.AzureishWhite
import com.mrtnmrls.devhub.presentation.ui.theme.DarkElectricBlue
import com.mrtnmrls.devhub.presentation.ui.theme.DevhubTheme

@Composable
internal fun BottomAppBarView(modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier.height(50.dp),
        contentColor = AzureishWhite,
        containerColor = DarkElectricBlue
    ) {
        Text(text = "Made by Martin with 🧠")
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
