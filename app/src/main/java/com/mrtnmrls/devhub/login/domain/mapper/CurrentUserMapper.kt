package com.mrtnmrls.devhub.login.domain.mapper

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.mrtnmrls.devhub.login.domain.model.CurrentUser

class CurrentUserMapper {
    fun transform(firebaseUser: FirebaseUser): CurrentUser {
        return CurrentUser(
            displayName = firebaseUser.displayName.orEmpty(),
            email = firebaseUser.email.orEmpty(),
            photoUrl = firebaseUser.photoUrl ?: Uri.EMPTY,
            providerId = firebaseUser.providerId,
            tenantId = firebaseUser.tenantId.orEmpty(),
            uid = firebaseUser.uid
        )
    }
}
