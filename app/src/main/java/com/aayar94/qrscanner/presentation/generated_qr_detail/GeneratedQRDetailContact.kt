package com.aayar94.qrscanner.presentation.generated_qr_detail

import android.graphics.Bitmap
import com.aayar94.qrscanner.domain.model.QRCategory
import com.aayar94.qrscanner.presentation.base.BaseUIAction
import com.aayar94.qrscanner.presentation.base.BaseUIEffect
import com.aayar94.qrscanner.presentation.base.BaseUIState

object GeneratedQRDetailContact {

    data class UiState(
        override val isLoading: Boolean = false,
        val qrProxy: String? = null,
        val qrBitmap: Bitmap? = null,
        val qrCategory: QRCategory? = null,
    ) : BaseUIState

    sealed class UiEffect : BaseUIEffect {
        data object OnNavigateBack : UiEffect()
        data object OnSaveQr : UiEffect()
    }


    sealed class UiAction : BaseUIAction {
        data object OnBackPressed : UiAction()
        data class InitUI(val bitmap: Bitmap, val qrCategory: QRCategory, val qrProxy: String) :
            UiAction()

        data class SaveQrCode(val qrCode: Bitmap, val category: QRCategory, val qrProxy: String) :
            UiAction()
    }

}