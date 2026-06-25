package com.aayar94.qrscanner.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.data.local.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsScreenConstruct.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<SettingsScreenConstruct.UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<SettingsScreenConstruct.UiEffect> = _uiEffect.receiveAsFlow()

    init {
        viewModelScope.launch {
            combine(
                dataStoreRepository.isVibrateEnabled,
                dataStoreRepository.isBeepEnabled
            ) { vibrate, beep -> vibrate to beep }
                .collect { (vibrate, beep) ->
                    _uiState.value = _uiState.value.copy(vibrate = vibrate, beep = beep)
                }
        }
    }

    fun onAction(action: SettingsScreenConstruct.UiAction) {
        when (action) {
            SettingsScreenConstruct.UiAction.OnBackPressed -> {
                viewModelScope.launch { _uiEffect.send(SettingsScreenConstruct.UiEffect.OnNavigateBack) }
            }

            is SettingsScreenConstruct.UiAction.OnBeepSettingsChanged -> {
                viewModelScope.launch { dataStoreRepository.saveBeepState(action.beep) }
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
                viewModelScope.launch { dataStoreRepository.saveVibrateState(action.vibrate) }
            }
        }
    }
}