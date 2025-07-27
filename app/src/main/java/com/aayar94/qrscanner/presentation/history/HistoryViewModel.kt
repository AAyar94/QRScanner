package com.aayar94.qrscanner.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.data.local.database.HistoryItemEntity
import com.aayar94.qrscanner.data.repository.QRScannerRepository
import com.aayar94.qrscanner.domain.model.HistoryItem
import com.aayar94.qrscanner.domain.model.toHistoryItem
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
class HistoryViewModel @Inject constructor(
    private val repository: QRScannerRepository
) : ViewModel() {

    val _uiState = MutableStateFlow(HistoryScreenContact.UiState())
    val uiState = _uiState.asStateFlow()

    val _uiEffect = Channel<HistoryScreenContact.UiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    var page = 1
    var selectedSection = 1

    fun onAction(action: HistoryScreenContact.UiAction) {
        when (action) {
            HistoryScreenContact.UiAction.LoadMore -> {}
            is HistoryScreenContact.UiAction.OnDeleteHistoryItem -> {
                deleteHistoryItem(action.historyItem)
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
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val list = if (selectedSection == 0) {
                repository.getAllScannedHistory()
            } else {
                repository.getAllCreatedHistory()
            }
            _uiState.update { it.copy(isLoading = false, list = list.map { it.toHistoryItem() }) }
        }
    }

    fun deleteHistoryItem(historyItem: HistoryItem) {
        viewModelScope.launch {
            repository.deleteHistoryItem(
                HistoryItemEntity(
                    historyItem.id,
                    historyItem.qrCode,
                    historyItem.uriProxy,
                    historyItem.category,
                    historyItem.time,
                    if (selectedSection == 0) false else true
                )
            )
            val currentList = uiState.value.list.toMutableList()
            currentList.remove(historyItem)
            _uiState.update { it.copy(list = currentList) }
        }
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