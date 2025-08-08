package com.aayar94.qrscanner.presentation.history

import com.aayar94.qrscanner.domain.model.HistoryItem
import com.aayar94.qrscanner.presentation.base.BaseUIAction
import com.aayar94.qrscanner.presentation.base.BaseUIEffect
import com.aayar94.qrscanner.presentation.base.BaseUIState

object HistoryScreenContact {

    data class UiState(
        override val isLoading: Boolean = false,
        val page: Int = 0,
        val selectedSection: Int = 0,
        val list: List<HistoryItem> = emptyList()
    ) : BaseUIState

    sealed class UiEffect : BaseUIEffect {
        object OnNavigateBack : UiEffect()
        object OnNavigateSettings : UiEffect()
        class OnNavigateDetail(val historyItem: HistoryItem) : UiEffect()
    }

    sealed class UiAction : BaseUIAction {
        data object OnBackPressed : UiAction()
        data class OnSectionSelected(val section: Int) : UiAction()
        data object OnSettingsClicked : UiAction()
        data class OnDeleteHistoryItem(val historyItem: HistoryItem) : UiAction()
        data class OnNavigateToDetail(val historyItem: HistoryItem) : UiAction()
        data object LoadMore : UiAction()
    }

}