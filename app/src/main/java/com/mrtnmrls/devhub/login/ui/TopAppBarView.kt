package com.mrtnmrls.devhub.login.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrtnmrls.devhub.landing.presentation.LandingIntent
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopAppBarView(
    modifier: Modifier = Modifier,
    onLandingIntent: (LandingIntent) -> Unit
) {
    TopAppBar(
        modifier = modifier
            .padding(top = 36.dp),
        title = {
            Text(
                text = "DevHub",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        ),
        actions = {
            IconButton(
                modifier = Modifier,
                onClick = { onLandingIntent(LandingIntent.OnProfileDialogShow) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account profile"
                )
            }
        }
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewTopAppBarViewDark() {
    DevhubTheme {
        Surface {
            TopAppBarView {}
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun PreviewTopAppBarViewLight() {
    DevhubTheme {
        Surface {
            TopAppBarView {}
        }
    }
}
