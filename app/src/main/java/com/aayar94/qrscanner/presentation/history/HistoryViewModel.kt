package com.aayar94.qrscanner.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.domain.model.HistoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HistoryViewModel @Inject constructor() : ViewModel() {

    val _uiState = MutableStateFlow(HistoryScreenContact.UiState())
    val uiState = _uiState.asStateFlow()

    val _uiEffect = Channel<HistoryScreenContact.UiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    var page = 1
    var selectedSection = 1

    fun onAction(action: HistoryScreenContact.UiAction) {
        when (action) {
            HistoryScreenContact.UiAction.LoadMore -> TODO()
            is HistoryScreenContact.UiAction.OnDeleteHistoryItem -> {

            }

            is HistoryScreenContact.UiAction.OnSectionSelected -> {
                onSectionSelected(action.section)
            }

            HistoryScreenContact.UiAction.OnSettingsClicked -> {
                viewModelScope.launch {
                    _uiEffect.send(HistoryScreenContact.UiEffect.OnNavigateSettings)
                }
            }

            is HistoryScreenContact.UiAction.OnNavigateToDetail -> {
                viewModelScope.launch {
                    _uiEffect.send(HistoryScreenContact.UiEffect.OnNavigateDetail(action.historyItem))
                }
            }
        }
    }

    fun loadData() {
        page = 1

    }

    fun loadMore() {
        page += 1
    }

    fun deleteHistoryItem(historyItem: HistoryItem) {

    }

    fun onSectionSelected(section: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedSection = section, list = emptyList()) }
            selectedSection = section
            delay(50)
            loadData()
        }
    }
}