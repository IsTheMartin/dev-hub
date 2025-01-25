package com.mrtnmrls.devhub.common.data.repository

import android.content.Context
import androidx.annotation.StringRes
import com.mrtnmrls.devhub.common.domain.repository.ResourceProvider
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    private val context: Context
): ResourceProvider {
    override fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }
}
