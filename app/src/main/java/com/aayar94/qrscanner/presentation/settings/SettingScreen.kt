package com.aayar94.qrscanner.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow

@Composable
fun SettingsScreenContainer(onBackPressed: () -> Unit) {
    val vm: SettingsViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val uiEffect by vm.uiEffect.collectAsStateWithLifecycle(null)
    val uiAction = vm::onAction
    val context = LocalContext.current
    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            SettingsScreenConstruct.UiEffect.OnNavigateBack -> {
                onBackPressed.invoke()
            }

            SettingsScreenConstruct.UiEffect.OnPrivacyPolicyClicked -> {

            }

            SettingsScreenConstruct.UiEffect.OnRateUsClicked -> {

            }

            SettingsScreenConstruct.UiEffect.OnShareClicked -> {

            }

            null -> {}
        }
    }

    SettingsScreen(uiState, uiEffect, uiAction)
}

@Composable
private fun SettingsScreen(
    uiState: SettingsScreenConstruct.UiState,
    uiEffect: SettingsScreenConstruct.UiEffect?,
    uiAction: (SettingsScreenConstruct.UiAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = { uiAction.invoke(SettingsScreenConstruct.UiAction.OnBackPressed) },
                modifier = Modifier
                    .background(Color.Black, shape = RoundedCornerShape(12.dp))
                    .shadow(5.dp, RoundedCornerShape(12.dp), ambientColor = Gray)
            ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Yellow
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(text = "Settings", color = Yellow)
            Spacer(Modifier.height(12.dp))
            SettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                name = "Vibrate",
                description = "Vibration when scan is done",
                icon = R.drawable.ic_vibrate,
                hasSwitch = true,
                switchChecked = uiState.vibrate,
                onClick = {
                    uiAction.invoke(
                        SettingsScreenConstruct.UiAction.OnVibrateSettingsChanged(
                            uiState.vibrate.not()
                        )
                    )
                }
            )
            SettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                name = "Beep",
                description = "Beep when scan is done",
                icon = R.drawable.ic_beep,
                hasSwitch = true,
                switchChecked = uiState.beep,
                onClick = {
                    uiAction.invoke(SettingsScreenConstruct.UiAction.OnBeepSettingsChanged(uiState.beep.not()))
                }
            )
            Spacer(Modifier.height(12.dp))
            Text(text = "Support", color = Yellow)
            Spacer(Modifier.height(12.dp))
            SettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                name = "Rate Us",
                description = "Your best reward to us",
                icon = R.drawable.ic_rate,
                hasSwitch = false,
                switchChecked = false,
                onClick = {
                    uiAction.invoke(SettingsScreenConstruct.UiAction.OnRateUsClicked)
                }
            )
            SettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                name = "Privacy Policy",
                description = "Follow our policies that benefits you.",
                icon = R.drawable.ic_privacy_policy,
                hasSwitch = false,
                switchChecked = false,
                onClick = {
                    uiAction.invoke(SettingsScreenConstruct.UiAction.OnPrivacyPolicyClicked)
                }
            )
            SettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                name = "Share",
                description = "Share app with others",
                icon = R.drawable.ic_share,
                hasSwitch = false,
                switchChecked = false,
                onClick = {
                    uiAction.invoke(SettingsScreenConstruct.UiAction.OnShareClicked)
                }
            )
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    QRScannerTheme {
        SettingsScreen(
            SettingsScreenConstruct.UiState(
                isLoading = false, vibrate = true, beep = true
            ), null
        ) {}
    }
}