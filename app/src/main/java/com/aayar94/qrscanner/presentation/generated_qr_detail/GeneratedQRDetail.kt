package com.aayar94.qrscanner.presentation.generated_qr_detail

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
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
import com.aayar94.qrscanner.core.theme.Yellow
import com.aayar94.qrscanner.domain.model.QRCategory

@Composable
fun GeneratedQRDetailScreenContainer(
    qrBitmap: Bitmap?,
    qrCategory: QRCategory,
    qrProxy: String,
    onNavigateBack: () -> Unit,
    onSaveQr: () -> Unit
) {
    val vm: GeneratedQRViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val uiEffect by vm.uiEffect.collectAsStateWithLifecycle(null)
    val uiAction = vm::onAction
    LaunchedEffect(qrBitmap, qrCategory, qrProxy) {
        uiAction.invoke(GeneratedQRDetailContact.UiAction.InitUI(qrBitmap!!, qrCategory, qrProxy))
    }
    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            GeneratedQRDetailContact.UiEffect.OnNavigateBack -> {
                onNavigateBack.invoke()
            }

            GeneratedQRDetailContact.UiEffect.OnSaveQr -> {
                onSaveQr.invoke()
            }

            null -> {}
        }
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
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .background(Color.Black, shape = RoundedCornerShape(12.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                uiState.qrBitmap?.asImageBitmap()
                    ?.let { Image(bitmap = it, contentDescription = null) }
                uiState.qrCategory?.name?.let {
                    Text(
                        text = stringResource(it),
                        color = Color.White
                    )
                }
                uiState.qrProxy?.let { Text(text = it, color = Color.White) }
            }
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Yellow, shape = RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share")

                    }
                    Text("Share", color = Color.White)
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Yellow, shape = RoundedCornerShape(24.dp))
                            .clickable {
                                uiState.qrBitmap?.let {
                                    uiState.qrCategory?.let { it1 ->
                                        uiState.qrProxy?.let { qrProxy ->
                                            uiAction.invoke(
                                                GeneratedQRDetailContact.UiAction.SaveQrCode(
                                                    qrCode = it,
                                                    category = it1,
                                                    qrProxy = qrProxy
                                                )
                                            )
                                        }
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.SaveAlt, contentDescription = "Save")

                    }
                    Text("Save", color = Color.White)
                }
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