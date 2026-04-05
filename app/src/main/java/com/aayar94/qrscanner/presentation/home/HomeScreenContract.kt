package com.aayar94.qrscanner.presentation.home

import android.net.Uri
import androidx.annotation.StringRes
import com.aayar94.qrscanner.presentation.base.BaseUIAction
import com.aayar94.qrscanner.presentation.base.BaseUIEffect
import com.aayar94.qrscanner.presentation.base.BaseUIState

object HomeScreenContract {
    data class UiState(
        override val isLoading: Boolean = false,
        @param:StringRes val errorMessage: Int? = null,
        val qrProxy: String? = null,
        val galleryQrResult: String? = null,
        val showNoQRDialog: Boolean = false,
    ) : BaseUIState

    sealed class UiAction : BaseUIAction {
        data class OnQRCodeScanned(val qrProxy: String) : UiAction()
        data class OnNavigateToDetail(val qrProxy: String) : UiAction()
        data object Retry : UiAction()
        data object DismissError : UiAction()
        data object NavigateToQRHistory : UiAction()
        data object NavigateToGenerate : UiAction()
        data object OnBackPressed : UiAction()
        data object OnGalleryClicked : UiAction()
        data class OnGalleryImagePicked(val uri: Uri) : UiAction()
        data object DismissGalleryDialogs : UiAction()
        data class OnOpenGalleryQR(val value: String) : UiAction()
    }

    sealed class UiEffect : BaseUIEffect {
        data object OnNavigateBack : UiEffect()
        data class ShowError(@param:StringRes val message: Int) : UiEffect()
        data class NavigateToQRDetail(val qrProxy: String) : UiEffect()
        class NavigateToQRHistory() : UiEffect()
        class NavigateToGenerate() : UiEffect()
        data object LaunchGallery : UiEffect()
        data class OpenUrl(val url: String) : UiEffect()
    }
}