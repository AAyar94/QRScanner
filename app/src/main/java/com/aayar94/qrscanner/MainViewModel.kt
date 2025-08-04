package com.aayar94.qrscanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aayar94.qrscanner.data.local.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _onboardingFinished = MutableStateFlow<Boolean?>(null)
    val onboardingFinished: StateFlow<Boolean?> = _onboardingFinished

    init {
        viewModelScope.launch {
            val finished = dataStoreRepository.isOnboardingFinished.first()
            _onboardingFinished.value = finished
        }
    }

}