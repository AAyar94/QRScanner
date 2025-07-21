package com.aayar94.qrscanner.presentation

import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.aayar94.qrscanner.domain.processImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning

@Composable
fun QrScannerView(
    onQrCodeScanned: (String) -> Unit,
    lensFacing: Int,
    flashEnabled: Boolean,
    zoomRatio: Float
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }

    // CameraControl state to be updated when zoomRatio or flash changes
    var cameraControl by remember { mutableStateOf<CameraControl?>(null) }

    // Rebind camera on lensFacing change
    key(lensFacing) {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }

            val barcodeScanner = BarcodeScanning.getClient()
            val analysisUseCase = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                        processImageProxy(barcodeScanner, imageProxy, onQrCodeScanned)
                    }
                }

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build()

            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, analysisUseCase
            )

            cameraControl = camera.cameraControl

            // Apply initial zoom and flash settings
            cameraControl?.setZoomRatio(zoomRatio)
            cameraControl?.enableTorch(flashEnabled)

        }, ContextCompat.getMainExecutor(context))
    }

    // 🔁 Update zoom dynamically
    LaunchedEffect(zoomRatio) {
        cameraControl?.setZoomRatio(zoomRatio)
    }

    // 🔁 Update flash dynamically
    LaunchedEffect(flashEnabled) {
        cameraControl?.enableTorch(flashEnabled)
    }

    AndroidView(factory = { previewView })
}