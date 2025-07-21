package com.aayar94.qrscanner.presentation.generate

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aayar94.qrscanner.core.theme.QRScannerTheme

@Composable
fun GenerateScreenContainer() {
    GenerateScreen()
}

@Composable
private fun GenerateScreen() {
    Box() {}
}

@Preview
@Composable
private fun GenerateScreenPreview() {
    QRScannerTheme {
        GenerateScreen()
    }
}