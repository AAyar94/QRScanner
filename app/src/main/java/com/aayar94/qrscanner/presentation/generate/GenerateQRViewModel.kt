package com.aayar94.qrscanner.presentation.generate

import androidx.lifecycle.ViewModel
import com.aayar94.qrscanner.presentation.generate_by_category.GenerateByCategoryContact
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class GenerateQRViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(GenerateQRContact.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<GenerateQRContact.UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<GenerateQRContact.UiEffect> = _uiEffect.receiveAsFlow()

    fun onAction(action: GenerateByCategoryContact.UiAction) {
        when (action) {
            is GenerateByCategoryContact.UiAction.OnCategorySelected -> {
                // TODO: save to local db  
            }
        }
    }

}