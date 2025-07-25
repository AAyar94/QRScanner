package com.aayar94.qrscanner.presentation.generate

import com.aayar94.qrscanner.domain.model.QRCategory

object GenerateQRContact {

    data class UiState(
        val isLoading: Boolean = false,
        val selectedCategory: QRCategory? = null,
        val uriText: String? = null
    )


    sealed class UiAction {
        data object onBackPressed : UiAction()
        data class OnUpdateUriText(val uriText: String) : UiAction()
        data class OnSaveQrCode(val qrProxy: String, val category: QRCategory) : UiAction()
        data class CategoryPickedInitalizeUI(val categoryId: Int) : UiAction()
        data class onPasteClicked(val text: String) : UiAction()
    }

    sealed class UiEffect {
        data object OnNavigateBack : UiEffect()
    }


}