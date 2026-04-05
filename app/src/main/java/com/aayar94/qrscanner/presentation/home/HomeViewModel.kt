package com.aayar94.qrscanner.presentation.home

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.presentation.home.HomeScreenContract.UiEffect.LaunchGallery
import com.aayar94.qrscanner.presentation.home.HomeScreenContract.UiEffect.NavigateToGenerate
import com.aayar94.qrscanner.presentation.home.HomeScreenContract.UiEffect.NavigateToQRDetail
import com.aayar94.qrscanner.presentation.home.HomeScreenContract.UiEffect.NavigateToQRHistory
import com.aayar94.qrscanner.presentation.home.HomeScreenContract.UiEffect.OpenUrl
import com.google.mlkit.vision.barcode.BarcodeScanning
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<HomeScreenContract.UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<HomeScreenContract.UiEffect> = _uiEffect.receiveAsFlow()

    private val barcodeScanner = BarcodeScanning.getClient()

    override fun onCleared() {
        super.onCleared()
        barcodeScanner.close()
    }

    fun onAction(action: HomeScreenContract.UiAction) {
        when (action) {
            HomeScreenContract.UiAction.DismissError -> dismissErrorState()

            is HomeScreenContract.UiAction.OnQRCodeScanned -> updateQRProxy(action.qrProxy)

            HomeScreenContract.UiAction.Retry -> onRetry()

            HomeScreenContract.UiAction.NavigateToGenerate -> {
                viewModelScope.launch { _uiEffect.send(NavigateToGenerate()) }
            }

            HomeScreenContract.UiAction.NavigateToQRHistory -> {
                viewModelScope.launch { _uiEffect.send(NavigateToQRHistory()) }
            }

            is HomeScreenContract.UiAction.OnNavigateToDetail -> {
                viewModelScope.launch { _uiEffect.send(NavigateToQRDetail(action.qrProxy)) }
            }

            is HomeScreenContract.UiAction.OnBackPressed -> {
                viewModelScope.launch { _uiEffect.send(HomeScreenContract.UiEffect.OnNavigateBack) }
            }

            HomeScreenContract.UiAction.OnGalleryClicked -> {
                viewModelScope.launch { _uiEffect.send(LaunchGallery) }
            }

            is HomeScreenContract.UiAction.OnGalleryImagePicked -> processGalleryImage(action.uri)

            HomeScreenContract.UiAction.DismissGalleryDialogs -> {
                _uiState.update { it.copy(galleryQrResult = null, showNoQRDialog = false) }
            }

            is HomeScreenContract.UiAction.OnOpenGalleryQR -> {
                _uiState.update { it.copy(galleryQrResult = null) }
                viewModelScope.launch { _uiEffect.send(OpenUrl(action.value)) }
            }
        }
    }

    private fun processGalleryImage(uri: Uri) {
        viewModelScope.launch {
            try {
                val inputImage = InputImage.fromFilePath(context, uri)
                barcodeScanner.process(inputImage)
                    .addOnSuccessListener { barcodes ->
                        val qrValue = barcodes.firstOrNull { it.rawValue != null }?.rawValue
                        if (qrValue != null) {
                            _uiState.update { it.copy(galleryQrResult = qrValue) }
                        } else {
                            _uiState.update { it.copy(showNoQRDialog = true) }
                        }
                    }
                    .addOnFailureListener {
                        _uiState.update { it.copy(showNoQRDialog = true) }
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(showNoQRDialog = true) }
            }
        }
    }

    fun updateQRProxy(qrProxy: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(qrProxy = qrProxy) }
        }
    }

    fun dismissErrorState() {
        viewModelScope.launch {
            _uiState.update { it.copy(errorMessage = null) }
        }
    }

    fun onRetry() {
        viewModelScope.launch {
            _uiState.update { it.copy(qrProxy = null) }
        }
    }
}
