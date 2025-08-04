package com.aayar94.qrscanner.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.presentation.home.HomeScreenContract.UiEffect.NavigateToGenerate
import com.aayar94.qrscanner.presentation.home.HomeScreenContract.UiEffect.NavigateToQRDetail
import com.aayar94.qrscanner.presentation.home.HomeScreenContract.UiEffect.NavigateToQRHistory
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

            HomeScreenContract.UiAction.NavigateToGenerate -> {
                viewModelScope.launch {
                    _uiEffect.send(NavigateToGenerate())
                }
            }

            HomeScreenContract.UiAction.NavigateToQRHistory -> {
                viewModelScope.launch {
                    _uiEffect.send(NavigateToQRHistory())
                }
            }

            is HomeScreenContract.UiAction.OnNavigateToDetail -> {
                viewModelScope.launch {
                    _uiEffect.send(NavigateToQRDetail(action.qrProxy))
                }
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