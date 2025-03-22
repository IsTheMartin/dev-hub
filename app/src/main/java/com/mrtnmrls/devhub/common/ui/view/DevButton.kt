package com.mrtnmrls.devhub.common.ui.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ),
        enabled = isEnabled
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
            contentColor = MaterialTheme.colorScheme.onSecondary,
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(text = buttonText)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PrimaryButtonViewDark() {
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

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun PrimaryButtonViewLight() {
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

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SecondaryButtonViewDark() {
    DevhubTheme {
        Surface {
            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                buttonText = "Secondary button"
            ) { }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun SecondaryButtonViewLight() {
    DevhubTheme {
        Surface {
            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                buttonText = "Secondary button"
            ) { }
        }
    }
}
