package com.mrtnmrls.devhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrtnmrls.devhub.domain.UseCaseResult
import com.mrtnmrls.devhub.domain.usecase.GetUserInformationUseCase
import com.mrtnmrls.devhub.domain.usecase.SignOutUseCase
import com.mrtnmrls.devhub.presentation.ui.effect.LandingEffect
import com.mrtnmrls.devhub.presentation.ui.intent.LandingIntent
import com.mrtnmrls.devhub.presentation.ui.state.LandingState
import com.mrtnmrls.devhub.presentation.ui.state.ProfileDialogState
import com.mrtnmrls.devhub.presentation.ui.state.UserProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserInformationUseCase: GetUserInformationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LandingState())
    val state = _state.asStateFlow()

    init {
        getUserLoggedInformation()
    }

    fun dispatchIntent(intent: LandingIntent) {
        when (intent) {
            LandingIntent.OnSignOut -> signOut()
            LandingIntent.OnProfileDialogDismiss -> dismissProfileDialog()
            LandingIntent.OnProfileDialogShow -> showProfileDialog()
        }
    }

    private fun getUserLoggedInformation() {
        viewModelScope.launch {
            when (val result = getUserInformationUseCase()) {
                is UseCaseResult.Failed -> {}
                is UseCaseResult.Success -> {
                    _state.update {
                        it.copy(
                            userProfileState = UserProfileState(
                                displayName = result.value.displayName,
                                email = result.value.email,
                                photoUrl = result.value.photoUrl
                            )
                        )
                    }
                }
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            when (signOutUseCase()) {
                is UseCaseResult.Failed -> Unit
                is UseCaseResult.Success -> {
                    _state.update {
                        it.copy(
                            effect = LandingEffect.OnSuccessfulSignOut,
                            profileDialogState = ProfileDialogState.Hidden
                        )
                    }
                }
            }
        }
    }

    private fun dismissProfileDialog() {
        _state.update { it.copy(profileDialogState = ProfileDialogState.Hidden) }
    }

    private fun showProfileDialog() {
        _state.update { it.copy(profileDialogState = ProfileDialogState.Visible) }
    }

}
