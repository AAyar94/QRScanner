package com.aayar94.qrscanner.presentation.generate_by_category

import com.aayar94.qrscanner.domain.model.QRCategory

object GenerateByCategoryContact {

    data class UiState(
        val loading: Boolean = false,
        val categoryList: List<QRCategory> = emptyList(),
    )

    sealed class UiAction {
        data class OnCategorySelected(val categoryId: Int) : UiAction()

    }

    sealed class UiEffect {
        data class OnNavigateToGenerateQR(val categoryId: Int) : UiEffect()
    }

}