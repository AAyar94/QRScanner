package com.aayar94.qrscanner.presentation.home

import androidx.annotation.StringRes
import com.aayar94.qrscanner.presentation.base.BaseUIAction
import com.aayar94.qrscanner.presentation.base.BaseUIEffect
import com.aayar94.qrscanner.presentation.base.BaseUIState

object HomeScreenContract {
    data class UiState(
        override val isLoading: Boolean = false,
        @param:StringRes val errorMessage: Int? = null,
        val qrProxy: String? = null
    ) : BaseUIState

    sealed class UiAction : BaseUIAction {
        data class OnQRCodeScanned(val qrProxy: String) : UiAction()
        data class OnNavigateToDetail(val qrProxy: String) : UiAction()
        data object Retry : UiAction()
        data object DismissError : UiAction()
        data object NavigateToQRHistory : UiAction()
        data object NavigateToGenerate : UiAction()
        data object OnBackPressed : UiAction()
    }

    sealed class UiEffect : BaseUIEffect {
        data object OnNavigateBack : UiEffect()
        data class ShowError(@param:StringRes val message: Int) : UiEffect()
        data class NavigateToQRDetail(val qrProxy: String) : UiEffect()
        class NavigateToQRHistory() : UiEffect()
        class NavigateToGenerate() : UiEffect()
    }
}