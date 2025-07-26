package com.aayar94.qrscanner.presentation.generate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.domain.use_case.GetQRCategoryListUseCase
import com.akansh.qrsmith.QRSmith
import com.akansh.qrsmith.model.QRCodeOptions
import com.akansh.qrsmith.model.QRStyles
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
                viewModelScope.launch {
                    _uiEffect.send(
                        GenerateQRContact.UiEffect.OnNavigateGeneratedQRDetail(
                            action.qrCode,
                            action.category,
                            action.qrProxy
                        )
                    )
                }
            }

            is GenerateQRContact.UiAction.onPasteClicked -> {
                pasteFromClipboard(action.text)
            }

            is GenerateQRContact.UiAction.OnUpdateUriText -> {
                updateUriText(action.uriText)
            }

            is GenerateQRContact.UiAction.OnGeneraQrCode -> {
                createQRCode(action.qrProxy)
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

    fun createQRCode(uriString: String) {
        val options = QRCodeOptions.Builder()
            .setWidth(500)
            .setHeight(500)
            .setForegroundColor(com.aayar94.qrscanner.R.color.black)
            .setBackgroundColor(com.aayar94.qrscanner.R.color.white)
            .setPatternStyle(QRStyles.PatternStyle.SQUARE)
            .setEyeFrameShape(QRStyles.EyeFrameShape.SQUARE)
            .setEyeBallShape(QRStyles.EyeBallShape.SQUARE)
            .setQuietZone(1)
            .build()

        try {
            val qrCode = QRSmith.generateQRCode(uriString, options)
            viewModelScope.launch {
                _uiState.update { it.copy(generatedQRCode = qrCode) }
            }
            onAction(
                GenerateQRContact.UiAction.OnSaveQrCode(
                    uiState.value.generatedQRCode!!,
                    uiState.value.selectedCategory!!,
                    uiState.value.uriText.toString()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}