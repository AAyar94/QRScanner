package com.aayar94.qrscanner.presentation.generate

import android.graphics.Bitmap
import com.aayar94.qrscanner.domain.model.QRCategory
import com.aayar94.qrscanner.presentation.base.BaseUIAction
import com.aayar94.qrscanner.presentation.base.BaseUIEffect
import com.aayar94.qrscanner.presentation.base.BaseUIState

object GenerateQRContact {

    data class UiState(
        override val isLoading: Boolean = false,
        val selectedCategory: QRCategory? = null,
        val uriText: String? = null,
        val generatedQRCode: Bitmap? = null,
    ) : BaseUIState


    sealed class UiAction : BaseUIAction {
        data object OnBackPressed : UiAction()
        data class OnUpdateUriText(val uriText: String) : UiAction()
        data class OnGeneraQrCode(
            val qrProxy: String,
            val category: QRCategory,
            val bgColor: Int,
            val fgColor: Int
        ) : UiAction()

        data class OnSaveQrCode(val qrCode: Bitmap, val category: QRCategory, val qrProxy: String) :
            UiAction()

        data class CategoryPickedInitalizeUI(val categoryId: Int) : UiAction()
        data class OnPasteClicked(val text: String) : UiAction()
    }

    sealed class UiEffect : BaseUIEffect {
        data object OnNavigateBack : UiEffect()
        data class OnNavigateGeneratedQRDetail(
            val qrCode: Bitmap,
            val category: QRCategory,
            val qrProxy: String
        ) :
            UiEffect()
    }


}