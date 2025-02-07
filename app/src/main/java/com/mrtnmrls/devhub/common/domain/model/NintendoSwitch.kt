package com.mrtnmrls.devhub.common.domain.model


import com.google.gson.annotations.SerializedName

data class NintendoSwitch(
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
