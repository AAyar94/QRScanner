package com.aayar94.qrscanner.presentation.history

import com.aayar94.qrscanner.domain.model.HistoryItem

object HistoryScreenContact {

    data class UiState(
        val isLoading: Boolean = false,
        val page: Int = 0,
        val selectedSection: Int = 0,
        val list: List<HistoryItem> = emptyList()
    )

    sealed class UiEffect {
        object OnNavigateBack : UiEffect()
        object OnNavigateSettings : UiEffect()
        class OnNavigateDetail(val historyItem: HistoryItem) : UiEffect()
    }

    sealed class UiAction {
        data object OnBackPressed : UiAction()
        data class OnSectionSelected(val section: Int) : UiAction()
        data object OnSettingsClicked : UiAction()
        data class OnDeleteHistoryItem(val historyItem: HistoryItem) : UiAction()
        data class OnNavigateToDetail(val historyItem: HistoryItem) : UiAction()
        data object LoadMore : UiAction()
    }

}