package com.aayar94.qrscanner.presentation.generate_by_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.domain.use_case.GetQRCategoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class GenerateByCategoryViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(GenerateByCategoryContact.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<GenerateByCategoryContact.UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<GenerateByCategoryContact.UiEffect> = _uiEffect.receiveAsFlow()


    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    loading = false,
                    categoryList =
                        GetQRCategoryListUseCase().getQRCategoriesList()
                )
            }
        }
    }

    fun onAction(action: GenerateByCategoryContact.UiAction) {
        when (action) {
            is GenerateByCategoryContact.UiAction.OnCategorySelected -> {
                viewModelScope.launch {
                    _uiEffect.send(GenerateByCategoryContact.UiEffect.OnNavigateToGenerateQR(action.categoryId))
                }
            }
        }
    }
}