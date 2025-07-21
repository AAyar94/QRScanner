package com.aayar94.qrscanner.presentation.qr_detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun QRDetailScreenContainer() {
    QRDetailScreen()
}

@Composable
private fun QRDetailScreen() {
}

@Preview
@Composable
private fun QRDetailScreenPreview() {
    com.aayar94.qrscanner.core.theme.QRScannerTheme {
        QRDetailScreen()
    }
}