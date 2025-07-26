package com.aayar94.qrscanner.presentation.generated_qr_detail

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.domain.model.QRCategory
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
class GeneratedQRViewModel @Inject constructor() : ViewModel() {

    val _uiState = MutableStateFlow(GeneratedQRDetailContact.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<GeneratedQRDetailContact.UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<GeneratedQRDetailContact.UiEffect> = _uiEffect.receiveAsFlow()

    fun onAction(action: GeneratedQRDetailContact.UiAction) {
        when (action) {
            GeneratedQRDetailContact.UiAction.OnNavigateBack -> {
                viewModelScope.launch {
                    _uiEffect.send(GeneratedQRDetailContact.UiEffect.OnNavigateBack)
                }
            }

            is GeneratedQRDetailContact.UiAction.initUI -> initUI(
                action.bitmap,
                action.qrCategory,
                action.qrProxy
            )
        }
    }

    fun initUI(bitmap: Bitmap, qrCategory: QRCategory, qrProxy: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    qrBitmap = bitmap,
                    qrCategory = qrCategory,
                    qrProxy = qrProxy
                )
            }
        }
    }
}