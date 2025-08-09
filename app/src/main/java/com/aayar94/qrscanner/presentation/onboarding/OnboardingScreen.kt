package com.aayar94.qrscanner.presentation.onboarding

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.core.RequestCameraPermission
import com.aayar94.qrscanner.core.component.CustomAlertDialog
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreenContainer(
    modifier: Modifier = Modifier,
    onPermissionResult: (Boolean) -> Unit,
    onPermissionNotGranted: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showAlertDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("For the scan qr code feature, we need access your camera permission.")
                Spacer(modifier = Modifier.height(8.dp))
                RequestCameraPermission() { permissionRequestResult ->
                    showBottomSheet = false
                    if (permissionRequestResult) {
                        onPermissionResult.invoke(permissionRequestResult)
                    } else {
                        showAlertDialog = true
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
    if (showAlertDialog) {
        CustomAlertDialog(
            onDismissRequest = {
                onPermissionNotGranted.invoke()
                showAlertDialog = false
            },
            onConfirmation = {
                showAlertDialog = false
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.setData(uri)
                context.startActivity(intent)
            },
            dialogTitle = "Camera Permission",
            dialogText = "We need camera permission to scan qr code",
            confirmButtonText = "Go to settings",
            icon = Icons.Filled.CameraAlt
        )
    }

    OnboardingScreen(
        onLetsGoClick = {
            showBottomSheet = true
            coroutineScope.launch {
                bottomSheetState.show()
            }
        }
    )
}

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier, onLetsGoClick: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Yellow),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .zIndex(2f)
                .align(Alignment.BottomCenter)
                .offset(y = 48.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                painter = painterResource(R.drawable.bg_splash_wave),
                contentDescription = null
            )
        }
        IconButton(
            onClick = { onLetsGoClick.invoke() },
            modifier = Modifier
                .padding(24.dp)
                .wrapContentSize()
                .zIndex(3f)
                .background(Yellow, CircleShape)
                .align(Alignment.BottomEnd)

        ) {
            Icon(
                modifier = Modifier.padding(8.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = Color.Black
            )
        }
        Column(
            Modifier
                .wrapContentSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(250.dp)
                    .aspectRatio(1f),
                painter = painterResource(R.drawable.ic_launcher_onboarding),
                contentDescription = null, contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Go and enjoy our features for free and make your life easy with us.",
                textAlign = TextAlign.Center, color = Gray
            )
            Spacer(Modifier.height(60.dp))
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    QRScannerTheme {
        OnboardingScreen(onLetsGoClick = {})
    }
}