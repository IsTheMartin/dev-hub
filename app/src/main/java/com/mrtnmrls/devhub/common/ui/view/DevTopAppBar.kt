package com.mrtnmrls.devhub.common.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mrtnmrls.devhub.common.ui.theme.AzureishWhite
import com.mrtnmrls.devhub.common.ui.theme.DarkElectricBlue
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
    },
    colors = TopAppBarColors(
        containerColor = DarkElectricBlue,
        scrolledContainerColor = DarkElectricBlue,
        navigationIconContentColor = AzureishWhite,
        titleContentColor = AzureishWhite,
        actionIconContentColor = AzureishWhite
    )
)

@Preview
@Composable
private fun PreviewDevTopAppBar() {
    DevhubTheme {
        Surface {
            DevTopAppBar(
                title = "My new top app bar"
            ) { }
        }
    }
}
