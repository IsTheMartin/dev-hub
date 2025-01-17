package com.mrtnmrls.devhub.esp8266.presentation

import com.mrtnmrls.devhub.esp8266.domain.model.Esp8266Values

data class Esp8266State(
    val uiState: Esp8266ScreenState = Esp8266ScreenState.Loading
)

sealed class Esp8266ScreenState {
    data object Loading : Esp8266ScreenState()
    data class SuccessfulContent(val values: Esp8266Values = Esp8266Values()) : Esp8266ScreenState()
    data object Error : Esp8266ScreenState()
}
