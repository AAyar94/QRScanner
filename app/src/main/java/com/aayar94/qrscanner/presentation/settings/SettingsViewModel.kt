package com.aayar94.qrscanner.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    val _uiState = MutableStateFlow(SettingsScreenConstruct.UiState())
    val uiState = _uiState.asStateFlow()

    val _uiEffect = Channel<SettingsScreenConstruct.UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<SettingsScreenConstruct.UiEffect> = _uiEffect.receiveAsFlow()

    fun onAction(action: SettingsScreenConstruct.UiAction) {
        when (action) {
            SettingsScreenConstruct.UiAction.OnBackPressed -> {
                viewModelScope.launch { _uiEffect.send(SettingsScreenConstruct.UiEffect.OnNavigateBack) }
            }

            is SettingsScreenConstruct.UiAction.OnBeepSettingsChanged -> {
                _uiState.value = _uiState.value.copy(beep = action.beep)
            }

            SettingsScreenConstruct.UiAction.OnPrivacyPolicyClicked -> {
                viewModelScope.launch { _uiEffect.send(SettingsScreenConstruct.UiEffect.OnPrivacyPolicyClicked) }
            }

            SettingsScreenConstruct.UiAction.OnRateUsClicked -> {
                viewModelScope.launch { _uiEffect.send(SettingsScreenConstruct.UiEffect.OnRateUsClicked) }
            }

            SettingsScreenConstruct.UiAction.OnShareClicked -> {
                viewModelScope.launch { _uiEffect.send(SettingsScreenConstruct.UiEffect.OnShareClicked) }
            }

            is SettingsScreenConstruct.UiAction.OnVibrateSettingsChanged -> {
                _uiState.value = _uiState.value.copy(vibrate = action.vibrate)
            }
        }
    }
}