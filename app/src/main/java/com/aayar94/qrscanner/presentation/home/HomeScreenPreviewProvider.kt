package com.aayar94.qrscanner.presentation.home


import androidx.compose.ui.tooling.preview.PreviewParameterProvider


class HomeScreenPreviewProvider  : PreviewParameterProvider<HomeScreenContract.UiState>{
    override val values: Sequence<HomeScreenContract.UiState>
        get() = sequenceOf(
            HomeScreenContract.UiState(
                isLoading = false,
                errorMessage = null,
                qrProxy = ""
            )
        )
}