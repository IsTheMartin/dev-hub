package com.mrtnmrls.devhub.common.domain.repository

fun interface ResourceProvider {
    fun getString(stringResId: Int): String
}
