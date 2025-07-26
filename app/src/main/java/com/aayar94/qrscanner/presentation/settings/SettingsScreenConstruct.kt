package com.aayar94.qrscanner.presentation.settings

object SettingsScreenConstruct {

    data class UiState(
        val isLoading: Boolean = false,
        val vibrate: Boolean = true,
        val beep: Boolean = true,
    )

    sealed class UiEffect {
        data object OnNavigateBack : UiEffect()
        data object OnRateUsClicked : UiEffect()
        data object OnPrivacyPolicyClicked : UiEffect()
        data object OnShareClicked : UiEffect()
    }

    sealed class UiAction {
        data class OnVibrateSettingsChanged(val vibrate: Boolean) : UiAction()
        data class OnBeepSettingsChanged(val beep: Boolean) : UiAction()
        data object OnBackPressed : UiAction()
        data object OnRateUsClicked : UiAction()
        data object OnPrivacyPolicyClicked : UiAction()
        data object OnShareClicked : UiAction()
    }

}