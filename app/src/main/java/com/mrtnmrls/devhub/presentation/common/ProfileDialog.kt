package com.mrtnmrls.devhub.presentation.common

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mrtnmrls.devhub.presentation.ui.intent.LandingIntent
import com.mrtnmrls.devhub.presentation.ui.state.UserProfileState
import com.mrtnmrls.devhub.presentation.ui.theme.DevhubTheme

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
            .background(Color.DarkGray)
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
                tint = Color.White
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "DevHub",
            color = Color.White
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
            .background(Color.Gray)
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
    ) {
        Image(
            modifier = Modifier
                .size(48.dp),
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "",
            colorFilter = ColorFilter.tint(color = Color.White)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = userProfileState.displayName, color = Color.White)
            Text(text = userProfileState.email, color = Color.White)
        }
    }
}

@Composable
private fun SignOutButton(onSignOut: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        onClick = onSignOut,
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Sign out", color = Color.White)
    }
}

@Preview
@Composable
private fun PreviewProfileDialog() {
    DevhubTheme {
        Surface {
            ProfileDialog(
                userProfileState = UserProfileState(),
                onLandingIntent = {}
            )
        }
    }
}