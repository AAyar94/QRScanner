package com.aayar94.qrscanner.presentation

import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.concurrent.futures.await
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.aayar94.qrscanner.domain.processImageProxy
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning

@Composable
fun QrScannerView(
    onQrCodeScanned: (String) -> Unit,
    lensFacing: Int,
    flashEnabled: Boolean,
    zoomRatioState: State<Float>,
    onZoomLimitsChanged: (Float, Float) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = remember {
        ProcessCameraProvider.getInstance(context)
    }
    val previewView = remember { PreviewView(context) }

    var cameraControl by remember { mutableStateOf<CameraControl?>(null) }
    var activeScanner by remember { mutableStateOf<BarcodeScanner?>(null) }

    DisposableEffect(Unit) {
        onDispose {
            activeScanner?.close()
            activeScanner = null
        }
    }

    LaunchedEffect(lensFacing) {
        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.await()

        val preview = Preview.Builder().build().also {
            it.surfaceProvider = previewView.surfaceProvider
        }

        activeScanner?.close()
        val barcodeScanner = BarcodeScanning.getClient()
        activeScanner = barcodeScanner

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

        val cameraInfo = camera.cameraInfo
        onZoomLimitsChanged(
            cameraInfo.zoomState.value?.minZoomRatio ?: 1f,
            cameraInfo.zoomState.value?.maxZoomRatio ?: 5f
        )

        cameraControl?.setZoomRatio(zoomRatioState.value)
        cameraControl?.enableTorch(flashEnabled)
    }

    LaunchedEffect(zoomRatioState.value) {
        cameraControl?.setZoomRatio(zoomRatioState.value)
    }

    LaunchedEffect(flashEnabled) {
        cameraControl?.enableTorch(flashEnabled)
    }

    AndroidView(factory = { previewView })
}