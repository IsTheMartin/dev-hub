package com.mrtnmrls.devhub.common.ui.view

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevTopAppBar(
    title: String,
    onBackIconClicked: () -> Unit
) = TopAppBar(
    title = {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
    },
    navigationIcon = {
        IconButton(
            onClick = { onBackIconClicked() }
        ) {
            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
        }
    }
)

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDevTopAppBarDark() {
    DevhubTheme {
        Surface {
            DevTopAppBar(
                title = "My new top app bar"
            ) { }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun PreviewDevTopAppBarLight() {
    DevhubTheme {
        Surface {
            DevTopAppBar(
                title = "My new top app bar"
            ) { }
        }
    }
}
