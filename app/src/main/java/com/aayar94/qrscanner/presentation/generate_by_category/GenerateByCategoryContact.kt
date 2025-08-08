package com.aayar94.qrscanner.presentation.generate_by_category

import com.aayar94.qrscanner.domain.model.QRCategory
import com.aayar94.qrscanner.presentation.base.BaseUIAction
import com.aayar94.qrscanner.presentation.base.BaseUIEffect
import com.aayar94.qrscanner.presentation.base.BaseUIState

object GenerateByCategoryContact {

    data class UiState(
        override val isLoading: Boolean = false,
        val categoryList: List<QRCategory> = emptyList(),
    ) : BaseUIState

    sealed class UiAction : BaseUIAction {
        data object OnBackPressed : UiAction()
        data class OnCategorySelected(val categoryId: Int) : UiAction()
        data object OnSettingsSelected : UiAction()
    }

    sealed class UiEffect : BaseUIEffect {
        data object OnNavigateBack : UiEffect()
        data class OnNavigateToGenerateQR(val categoryId: Int) : UiEffect()
        data object OnSettingsSelected : UiEffect()
    }

}