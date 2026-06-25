package com.aayar94.qrscanner.presentation.generate

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.data.local.database.HistoryItemEntity
import com.aayar94.qrscanner.data.repository.QRScannerRepository
import com.aayar94.qrscanner.domain.model.QRCategory
import com.aayar94.qrscanner.domain.use_case.GetQRCategoryListUseCase
import com.aayar94.qrscanner.core.Constants.Companion.QR_CODE_SIZE
import com.aayar94.qrscanner.core.Constants.Companion.QR_QUIET_ZONE
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
import java.time.LocalDateTime


@HiltViewModel
class GenerateQRViewModel @Inject constructor(
    val repository: QRScannerRepository,
    private val getCategoryListUseCase: GetQRCategoryListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GenerateQRContact.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<GenerateQRContact.UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<GenerateQRContact.UiEffect> = _uiEffect.receiveAsFlow()

    fun onAction(action: GenerateQRContact.UiAction) {
        when (action) {

            GenerateQRContact.UiAction.OnBackPressed -> {
                viewModelScope.launch {
                    _uiEffect.send(GenerateQRContact.UiEffect.OnNavigateBack)
                }
            }

            is GenerateQRContact.UiAction.CategoryPickedInitalizeUI -> {
                setupUIState(action.categoryId)
            }

            is GenerateQRContact.UiAction.OnSaveQrCode -> {
                onCreatedQRCode(
                    action.qrCode,
                    action.category,
                    action.qrProxy
                )
            }

            is GenerateQRContact.UiAction.OnPasteClicked -> {
                pasteFromClipboard(action.text)
            }

            is GenerateQRContact.UiAction.OnUpdateUriText -> {
                updateUriText(action.uriText)
            }

            is GenerateQRContact.UiAction.OnGeneraQrCode -> {
                createQRCode(action.qrProxy, action.category, action.bgColor, action.fgColor)
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
                getCategoryListUseCase.getQRCategoriesList().find { it.id == categoryId }
            _uiState.update { it.copy(selectedCategory = category) }
        }
    }

    fun createQRCode(uriString: String, qrCategory: QRCategory, bgColor: Int, fgColor: Int) {
        val options = QRCodeOptions.Builder()
            .setWidth(QR_CODE_SIZE)
            .setHeight(QR_CODE_SIZE)
            .setForegroundColor(fgColor)
            .setBackgroundColor(bgColor)
            .setPatternStyle(QRStyles.PatternStyle.SQUARE)
            .setEyeFrameShape(QRStyles.EyeFrameShape.SQUARE)
            .setEyeBallShape(QRStyles.EyeBallShape.SQUARE)
            .setQuietZone(QR_QUIET_ZONE)
            .build()

        try {
            val qrCode = QRSmith.generateQRCode(uriString, options)
            viewModelScope.launch {
                _uiState.update { it.copy(generatedQRCode = qrCode) }
            }
            onAction(
                GenerateQRContact.UiAction.OnSaveQrCode(
                    qrCode,
                    qrCategory,
                    uiState.value.uriText.toString()
                )
            )
        } catch (e: Exception) {
            viewModelScope.launch {
                _uiEffect.send(GenerateQRContact.UiEffect.OnQRGenerationError(e.message ?: "Failed to generate QR code"))
            }
        }

    }

    fun onCreatedQRCode(
        qrCode: Bitmap,
        category: QRCategory,
        qrProxy: String
    ) {
        viewModelScope.launch {
            _uiEffect.send(
                GenerateQRContact.UiEffect.OnNavigateGeneratedQRDetail(
                    qrCode,
                    category,
                    qrProxy
                )
            )
        }
    }

}