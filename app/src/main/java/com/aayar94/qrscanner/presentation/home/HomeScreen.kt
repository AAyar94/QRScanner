package com.aayar94.qrscanner.presentation.home

import androidx.camera.core.CameraSelector
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.BrowseGallery
import androidx.compose.material.icons.outlined.Cameraswitch
import androidx.compose.material.icons.outlined.FlashlightOn
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aayar94.qrscanner.core.theme.theme.QRScannerTheme
import com.aayar94.qrscanner.presentation.QrScannerView


@Composable
fun HomeScreenContainer(modifier: Modifier = Modifier) {
    val vm: HomeViewModel = hiltViewModel<HomeViewModel>()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val uiEffect by vm.uiEffect.collectAsStateWithLifecycle(null)
    val onAction = vm::onAction
    HomeScreen(uiState, uiEffect, onAction)
}

@Composable
fun HomeScreen(
    uiState: HomeScreenContract.UiState,
    uiEffect: HomeScreenContract.UiEffect?,
    onAction: (HomeScreenContract.UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        QrScannerScreen(modifier = Modifier, {
            onAction.invoke(HomeScreenContract.UiAction.OnQRCodeScanned(it))
        })
        /*Row(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .wrapContentHeight()
                .padding(top = 48.dp)
                .padding(horizontal = 8.dp)
                .align(Alignment.TopCenter)
                .background(
                    Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(24.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier.padding(4.dp),
                imageVector = Icons.Outlined.BrowseGallery,
                tint = Color.White,
                contentDescription = "Gallery"
            )
            Icon(
                modifier = Modifier.padding(4.dp),
                imageVector = Icons.Outlined.FlashlightOn,
                tint = Color.White,
                contentDescription = "Flashlight"
            )
            Icon(
                modifier = Modifier.padding(4.dp),
                imageVector = Icons.Outlined.Cameraswitch,
                tint = Color.White,
                contentDescription = "Camera Switch"
            )
        }*/
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .wrapContentHeight()
                .padding(bottom = 48.dp)
                .align(
                    Alignment.BottomCenter
                )
                .padding(horizontal = 8.dp)

        ) {
            AnimatedVisibility(
                visible = uiState.qrProxy != null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(
                        bottom = 12.dp
                    )
                    .zIndex(3f),
                enter = fadeIn() + expandIn(),
                exit = fadeOut() + shrinkOut()
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Red, shape = CircleShape)
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.QrCode, tint = Color.White, contentDescription = "Scanned QR"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .wrapContentHeight()
                    .align(Alignment.BottomCenter)
                    .background(
                        Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(24.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Outlined.QrCode,
                    tint = Color.White,
                    contentDescription = "Generate"
                )
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Outlined.History,
                    tint = Color.White,
                    contentDescription = "History"
                )
            }
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewProvider::class) uiState: HomeScreenContract.UiState,
) {
    QRScannerTheme {
        HomeScreen(uiState, null, {})
    }
}

@Composable
fun QrScannerScreen(modifier: Modifier = Modifier, result: (String) -> Unit) {
    var scannedText by remember { mutableStateOf<String?>(null) }
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    var flashEnabled by remember { mutableStateOf(false) }
    var zoomRatio by remember { mutableFloatStateOf(1f) }

    Box(modifier = modifier.fillMaxSize()) {
        QrScannerView(
            onQrCodeScanned = {
                scannedText = it
                result.invoke(it)
            },
            lensFacing = lensFacing,
            flashEnabled = flashEnabled,
            zoomRatio = zoomRatio
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 150.dp)
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Filled.Remove,
                tint = Color.White,
                contentDescription = "Zoom Negative"
            )
            Slider(
                value = zoomRatio,
                onValueChange = { zoomRatio = it },
                valueRange = 0f..1f,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Filled.Add,
                tint = Color.White,
                contentDescription = "Zoom Positive"
            )
        }

        // Flash & Camera Switch Controls
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
                .background(
                    Color.Black.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                imageVector = Icons.Outlined.FlashlightOn,
                tint = if (flashEnabled) Color.Yellow else Color.White,
                contentDescription = "Flash",
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
                    .clickable { flashEnabled = !flashEnabled }
            )
            Icon(
                imageVector = Icons.Outlined.Cameraswitch,
                tint = Color.White,
                contentDescription = "Camera Switch",
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
                    .clickable {
                        lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK)
                            CameraSelector.LENS_FACING_FRONT
                        else
                            CameraSelector.LENS_FACING_BACK
                    }
            )
        }

        // Scanned Result
        scannedText?.let {
            Text(
                text = "QR Kodu: $it",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}
