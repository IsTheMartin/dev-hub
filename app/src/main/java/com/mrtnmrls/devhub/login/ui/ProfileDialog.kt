package com.mrtnmrls.devhub.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mrtnmrls.devhub.landing.presentation.LandingIntent
import com.mrtnmrls.devhub.landing.presentation.UserProfileState
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.common.ui.theme.Typography
import com.mrtnmrls.devhub.common.ui.view.SecondaryButton

@Composable
internal fun ProfileDialog(
    userProfileState: UserProfileState,
    onLandingIntent: (LandingIntent) -> Unit
) {
    val dialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true
    )
    Dialog(
        onDismissRequest = { onLandingIntent(LandingIntent.OnProfileDialogDismiss) },
        properties = dialogProperties
    ) {
        ProfileDialogContainer(
            userProfileState = userProfileState,
            onLandingIntent = onLandingIntent)
    }
}

@Composable
private fun ProfileDialogContainer(
    userProfileState: UserProfileState,
    onLandingIntent: (LandingIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            ProfileDialogTitle(
                onDismissDialog = { onLandingIntent(LandingIntent.OnProfileDialogDismiss) }
            )
            ProfileDialogContent(userProfileState)
            SignOutButton(
                onSignOut = { onLandingIntent(LandingIntent.OnSignOut) }
            )
        }
    }
}

@Composable
private fun ProfileDialogTitle(onDismissDialog: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onDismissDialog
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "DevHub",
            color = MaterialTheme.colorScheme.primary,
            style = Typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProfileDialogContent(
    userProfileState: UserProfileState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column {
            UserProfileInformation(userProfileState)
        }
    }
}

@Composable
private fun UserProfileInformation(
    userProfileState: UserProfileState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            modifier = Modifier
                .size(48.dp),
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = userProfileState.displayName,
                color = MaterialTheme.colorScheme.secondary,
                style = Typography.titleMedium,
                maxLines = 1
            )
            Text(
                text = userProfileState.email,
                color = MaterialTheme.colorScheme.secondary,
                style = Typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun SignOutButton(onSignOut: () -> Unit) {
    SecondaryButton (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        onClick = onSignOut,
        buttonText = "Sign out",
    )
}

@Preview
@Composable
private fun PreviewProfileDialog() {
    DevhubTheme {
        Surface {
            ProfileDialog(
                userProfileState = UserProfileState(
                    displayName = "Fulanito Escorza Jiménez Losa",
                    email = "fulanito_jimenez_losa@chokomilk.com"
                ),
                onLandingIntent = {}
            )
        }
    }
}
