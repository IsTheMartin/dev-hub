package com.mrtnmrls.devhub.esp8266.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrtnmrls.devhub.esp8266.domain.model.Esp8266Values
import com.mrtnmrls.devhub.domain.UseCaseResult
import com.mrtnmrls.devhub.esp8266.domain.usecase.GetEsp8266Data
import com.mrtnmrls.devhub.esp8266.domain.usecase.ToggleChristmasLightsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Esp8266ViewModel @Inject constructor(
    private val getEsp8266UseCase: GetEsp8266Data,
    private val toggleChristmasLightsUseCase: ToggleChristmasLightsUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(Esp8266State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getEsp8266UseCase().collect { data ->
                when(data) {
                    is UseCaseResult.Failed -> {
                        _state.update {
                            it.copy(uiState = Esp8266ScreenState.Error)
                        }
                    }
                    is UseCaseResult.Success -> {
                        _state.update {
                            val esp8266 = Esp8266Values(
                                christmasLights = data.value.christmasLights,
                                currentDateTime = data.value.currentDateTime,
                                pinState = data.value.pinState,
                                timeAlive = data.value.timeAlive.toLong()
                            )
                            it.copy(
                                uiState = Esp8266ScreenState.SuccessfulContent(esp8266)
                            )
                        }
                    }
                }
            }
        }
    }

    internal fun toggleChristmasLightsState() {
        viewModelScope.launch {
            val uiState = _state.value.uiState
            if (uiState is Esp8266ScreenState.SuccessfulContent) {
                val currentChristmasLightsState = uiState.values.christmasLights
                when (val result = toggleChristmasLightsUseCase(currentChristmasLightsState)) {
                    is UseCaseResult.Failed -> {
                        println("MRTN > ${result.error}")
                    }

                    is UseCaseResult.Success -> {
                        println("MRTN > Yey!")
                    }
                }
            }
        }
    }
}
