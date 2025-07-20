package com.aayar94.qrscanner.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() :
    ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<HomeScreenContract.UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<HomeScreenContract.UiEffect> = _uiEffect.receiveAsFlow()

    fun onAction(action: HomeScreenContract.UiAction) {
        when (action) {
            HomeScreenContract.UiAction.DismissError -> {
                dismissErrorState()
            }

            is HomeScreenContract.UiAction.OnQRCodeScanned -> {
                updateQRProxy(action.qrProxy)
            }

            HomeScreenContract.UiAction.Retry -> {
                onRetry()
            }
        }
    }

    fun updateQRProxy(qrProxy: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(qrProxy = qrProxy) }
        }
    }

    fun dismissErrorState() {
        viewModelScope.launch {
            _uiState.update { it.copy(errorMessage = null) }
        }
    }

    fun onRetry() {
        viewModelScope.launch {
            _uiState.update { it.copy(qrProxy = null) }
        }
    }
}