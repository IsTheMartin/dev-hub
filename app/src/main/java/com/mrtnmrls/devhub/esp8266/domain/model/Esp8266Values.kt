package com.mrtnmrls.devhub.esp8266.domain.model

data class Esp8266Values(
    val christmasLights: Boolean = false,
    val currentDateTime: String = "",
    val pinState: Boolean = false,
    val timeAlive: Long = 0L
)
