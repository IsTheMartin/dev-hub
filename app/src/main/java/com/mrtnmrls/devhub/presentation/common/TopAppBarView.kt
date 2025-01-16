package com.mrtnmrls.devhub.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrtnmrls.devhub.presentation.ui.intent.LandingIntent
import com.mrtnmrls.devhub.presentation.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.presentation.ui.theme.Khaki
import com.mrtnmrls.devhub.presentation.ui.theme.MetallicBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopAppBarView(
    modifier: Modifier = Modifier,
    onLandingIntent: (LandingIntent) -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = "DevHub") },
        colors = TopAppBarColors(
            containerColor = MetallicBlue,
            titleContentColor = Khaki,
            scrolledContainerColor = Khaki,
            navigationIconContentColor = Khaki,
            actionIconContentColor = Khaki
        ),
        actions = {
            IconButton(
                modifier = Modifier.padding(horizontal = 20.dp),
                onClick = { onLandingIntent(LandingIntent.OnProfileDialogShow) }
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewTopAppBarView() {
    DevhubTheme {
        Surface {
            TopAppBarView {}
        }
    }
}
