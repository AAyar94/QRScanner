package com.aayar94.qrscanner.presentation.generate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.domain.use_case.GetQRCategoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class GenerateQRViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(GenerateQRContact.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<GenerateQRContact.UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<GenerateQRContact.UiEffect> = _uiEffect.receiveAsFlow()

    fun onAction(action: GenerateQRContact.UiAction) {
        when (action) {

            GenerateQRContact.UiAction.onBackPressed -> {
                viewModelScope.launch {
                    _uiEffect.send(GenerateQRContact.UiEffect.OnNavigateBack)
                }
            }

            is GenerateQRContact.UiAction.CategoryPickedInitalizeUI -> {
                setupUIState(action.categoryId)
            }

            is GenerateQRContact.UiAction.OnSaveQrCode -> {

            }

            is GenerateQRContact.UiAction.onPasteClicked -> {
                pasteFromClipboard(action.text)
            }

            is GenerateQRContact.UiAction.OnUpdateUriText -> {
                updateUriText(action.uriText)
            }
        }
    }

    private fun updateUriText(uriText: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(uriText = uriText) }
        }
    }

    fun pasteFromClipboard(text: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(uriText = text) }
        }
    }

    fun setupUIState(categoryId: Int) {
        viewModelScope.launch {
            val category =
                GetQRCategoryListUseCase().getQRCategoriesList().find { it.id == categoryId }
            _uiState.update { it.copy(selectedCategory = category) }
        }
    }


}