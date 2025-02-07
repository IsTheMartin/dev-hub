package com.mrtnmrls.devhub.common.domain.model


import com.google.gson.annotations.SerializedName

data class NintendoSwitchGames(
    @SerializedName("nintendoSwitch")
    val nintendoSwitch: List<NintendoSwitch>
)
