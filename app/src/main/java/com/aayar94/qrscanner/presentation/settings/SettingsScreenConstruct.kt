package com.aayar94.qrscanner.presentation.settings

import com.aayar94.qrscanner.presentation.base.BaseUIAction
import com.aayar94.qrscanner.presentation.base.BaseUIEffect
import com.aayar94.qrscanner.presentation.base.BaseUIState

object SettingsScreenConstruct {

    data class UiState(
        override val isLoading: Boolean = false,
        val vibrate: Boolean = true,
        val beep: Boolean = true,
    ) : BaseUIState

    sealed class UiEffect : BaseUIEffect {
        data object OnNavigateBack : UiEffect()
        data object OnRateUsClicked : UiEffect()
        data object OnPrivacyPolicyClicked : UiEffect()
        data object OnShareClicked : UiEffect()
    }

    sealed class UiAction : BaseUIAction {
        data object OnBackPressed : UiAction()
        data class OnVibrateSettingsChanged(val vibrate: Boolean) : UiAction()
        data class OnBeepSettingsChanged(val beep: Boolean) : UiAction()
        data object OnRateUsClicked : UiAction()
        data object OnPrivacyPolicyClicked : UiAction()
        data object OnShareClicked : UiAction()
    }

}