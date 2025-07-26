package com.aayar94.qrscanner.presentation.generated_qr_detail

import android.graphics.Bitmap
import com.aayar94.qrscanner.domain.model.QRCategory

object GeneratedQRDetailContact {

    data class UiState(
        val isLoading: Boolean = false,
        val qrProxy: String? = null,
        val qrBitmap: Bitmap? = null,
        val qrCategory: QRCategory? = null,
    )

    sealed class UiEffect {
        data object OnNavigateBack : UiEffect()
    }


    sealed class UiAction {
        data object OnNavigateBack : UiAction()
        data class initUI(val bitmap: Bitmap, val qrCategory: QRCategory, val qrProxy: String) : UiAction()
    }

}