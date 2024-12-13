package com.mrtnmrls.devhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrtnmrls.devhub.domain.UseCaseResult
import com.mrtnmrls.devhub.domain.usecase.IsUserLoggedUseCase
import com.mrtnmrls.devhub.domain.usecase.LoginUseCase
import com.mrtnmrls.devhub.presentation.ui.effect.LoginEffect
import com.mrtnmrls.devhub.presentation.ui.intent.LoginIntent
import com.mrtnmrls.devhub.presentation.ui.state.LoginState
import com.mrtnmrls.devhub.presentation.ui.state.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val isUserLoggedUseCase: IsUserLoggedUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    init {
        isUserLogged()
    }

    internal fun dispatchIntent(intent: LoginIntent) {
        when (intent) {
            LoginIntent.OnContinueWithoutLogin -> loginAnonymously()
            is LoginIntent.OnLogin -> login(intent.email, intent.password)
        }
    }

    private fun isUserLogged() = viewModelScope.launch {
        when (val isUserLogged = isUserLoggedUseCase()) {
            is UseCaseResult.Failed -> {
                _state.update {
                    it.copy(screenState = LoginScreenState.Error)
                }
            }

            is UseCaseResult.Success -> {
                if (isUserLogged.value) {
                    _state.update {
                        it.copy(effect = LoginEffect.OnSuccessfulLogin)
                    }
                } else {
                    _state.update {
                        it.copy(screenState = LoginScreenState.SuccessContent())
                    }
                }
            }
        }

    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(screenState = LoginScreenState.SuccessContent(isLogIn = true))
            }
            when (val loginResult = loginUseCase.invoke(email, password)) {
                is UseCaseResult.Failed -> {
                    println("MRTN > {${loginResult.error.message}}")
                    _state.update {
                        it.copy(
                            screenState = LoginScreenState.SuccessContent(
                                isMissingEmail = true,
                                isMissingPassword = true
                            )
                        )
                    }
                }

                is UseCaseResult.Success -> {
                    _state.update {
                        it.copy(effect = LoginEffect.OnSuccessfulLogin)
                    }
                }
            }
        }
    }

    private fun loginAnonymously() {
        // todo: implement logic to only display some features
    }
}
