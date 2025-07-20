package com.aayar94.qrscanner.presentation.home

import androidx.annotation.StringRes

object HomeScreenContract {
    data class UiState(
        val isLoading: Boolean = false,
        @StringRes val errorMessage: Int? = null,
        val qrProxy: String? = null
    )

    sealed class UiAction {
        data class OnQRCodeScanned(val qrProxy: String) : UiAction()
        data object Retry : UiAction()
        data object DismissError : UiAction()
    }

    sealed class UiEffect {

        data class ShowError(@StringRes val message: Int) : UiEffect()
        data class NavigateToQRDetail(val qrProxy: String) : UiEffect()
    }
}