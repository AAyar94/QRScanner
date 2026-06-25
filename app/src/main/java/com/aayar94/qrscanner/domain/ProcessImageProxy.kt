package com.aayar94.qrscanner.domain

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

/**
 * Runs ML Kit barcode scanning on [imageProxy] and invokes [onQrCodeScanned] with the raw value
 * of the first detected barcode. Always closes [imageProxy] when done.
 */
@OptIn(ExperimentalGetImage::class)
fun processImageProxy(
    barcodeScanner: BarcodeScanner,
    imageProxy: ImageProxy,
    onQrCodeScanned: (String) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    barcode.rawValue?.let { value ->
                        Timber.d("QR value: $value")
                        onQrCodeScanned(value)
                    }
                }
            }
            .addOnFailureListener {
                Timber.e(it, "Scan failed")
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }

}
