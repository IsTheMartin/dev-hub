package com.mrtnmrls.devhub.login.domain.model

import android.net.Uri

data class CurrentUser(
    val displayName: String,
    val email: String,
    val photoUrl: Uri,
    val providerId: String,
    val tenantId: String,
    val uid: String
)
