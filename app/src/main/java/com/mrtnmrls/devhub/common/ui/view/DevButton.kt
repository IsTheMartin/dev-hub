package com.mrtnmrls.devhub.common.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrtnmrls.devhub.common.ui.theme.AzureishWhite
import com.mrtnmrls.devhub.common.ui.theme.DarkElectricBlue
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.common.ui.theme.JapaneseIndigo

@Composable
fun PrimaryButton(modifier: Modifier = Modifier, buttonText: String, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = buttonColors(
            contentColor = AzureishWhite,
            containerColor = DarkElectricBlue
        )
    ) {
        Text(text = buttonText)
    }
}

@Composable
fun SecondaryButton(modifier: Modifier = Modifier, buttonText: String, onClick: () -> Unit) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = outlinedButtonColors(
            contentColor = JapaneseIndigo,
            containerColor = AzureishWhite
        )
    ) {
        Text(text = buttonText)
    }
}

@Preview
@Composable
private fun PrimaryButtonView() {
    DevhubTheme {
        Surface {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                buttonText = "Primary button"
            ) { }
        }
    }
}

@Preview
@Composable
private fun SecondaryButtonView() {
    DevhubTheme {
        Surface {
            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                buttonText = "Primary button"
            ) { }
        }
    }
}
