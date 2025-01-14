package com.mrtnmrls.devhub.presentation.ui.state

import android.net.Uri
import com.mrtnmrls.devhub.presentation.ui.effect.LandingEffect

data class LandingState(
    val effect: LandingEffect = LandingEffect.NoOp,
    val profileDialogState: ProfileDialogState = ProfileDialogState.Hidden,
    val userProfileState: UserProfileState = UserProfileState()
)

sealed class ProfileDialogState {
    data object Visible : ProfileDialogState()
    data object Hidden : ProfileDialogState()
}

data class UserProfileState(
    val displayName: String = "",
    val email: String = "",
    val photoUrl: Uri = Uri.EMPTY
)
