package com.aayar94.qrscanner.presentation.history

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aayar94.qrscanner.core.theme.QRScannerTheme

@Composable
fun HistoryScreenContainer() {
    HistoryScreen()
}

@Composable
private fun HistoryScreen() {
    Box() {}
}

@Preview
@Composable
private fun HistoryScreenPreview() {
    QRScannerTheme {
        HistoryScreen()
    }
}