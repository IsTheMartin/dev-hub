package com.mrtnmrls.devhub.domain.model

import com.google.firebase.database.PropertyName

data class Esp8266(
    @get:PropertyName(CHRISTMAS_LIGHTS) @set:PropertyName(CHRISTMAS_LIGHTS) var christmasLights: Boolean = false,
    @get:PropertyName(CURRENT_DATE_TIME) @set:PropertyName(CURRENT_DATE_TIME) var currentDateTime: String = "",
    @get:PropertyName(PIN_STATE) @set:PropertyName(PIN_STATE) var pinState: Boolean = false,
    @get:PropertyName(TIME_ALIVE) @set:PropertyName(TIME_ALIVE) var timeAlive: Double = 0.0
) {
    companion object {
        private const val CHRISTMAS_LIGHTS = "christmas-lights"
        private const val CURRENT_DATE_TIME = "current-date-time"
        private const val PIN_STATE = "pin-state"
        private const val TIME_ALIVE = "time-alive"
    }
}
