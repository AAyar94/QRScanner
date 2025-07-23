package com.aayar94.qrscanner.presentation.generate

import com.aayar94.qrscanner.domain.model.QRCategory

object GenerateQRContact {

    data class UiState(
        val isLoading: Boolean = false,
        val selectedCategory: Int? = null
    )


    sealed class UiAction {
        data class OnSaveQrCode(val qrProxy: String, val category: QRCategory) : UiAction()
    }

    sealed class UiEffect {

    }


}