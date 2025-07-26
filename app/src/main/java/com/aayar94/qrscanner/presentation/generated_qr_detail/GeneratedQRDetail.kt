package com.aayar94.qrscanner.presentation.generated_qr_detail

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.domain.model.QRCategory

@Composable
fun GeneratedQRDetailScreenContainer(
    qrBitmap: Bitmap?,
    qrCategory: QRCategory,
    qrProxy: String
) {
    val vm: GeneratedQRViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val uiEffect by vm.uiEffect.collectAsStateWithLifecycle(null)
    val uiAction = vm::onAction
    LaunchedEffect(qrBitmap, qrCategory, qrProxy) {
        uiAction.invoke(GeneratedQRDetailContact.UiAction.initUI(qrBitmap!!, qrCategory, qrProxy))
    }
    GeneratedQRDetailScreen(uiState, uiEffect, uiAction)
}

@Composable
private fun GeneratedQRDetailScreen(
    uiState: GeneratedQRDetailContact.UiState,
    uiEffect: GeneratedQRDetailContact.UiEffect?,
    uiAction: (GeneratedQRDetailContact.UiAction) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .background(Color.Black, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                uiState.qrBitmap?.asImageBitmap()
                    ?.let { Image(bitmap = it, contentDescription = null) }
                uiState.qrCategory?.name?.let { Text(text = stringResource(it)) }
                uiState.qrProxy?.let { Text(text = it) }
            }
        }

    }
}

@Preview
@Composable
private fun GeneratedQRDetailScreenPreview() {
    QRScannerTheme {
        GeneratedQRDetailScreen(uiState = GeneratedQRDetailContact.UiState(), null) {}
    }
}