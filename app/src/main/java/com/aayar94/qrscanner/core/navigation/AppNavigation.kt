package com.aayar94.qrscanner.core.navigation

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.GENERATE_By_CATEGORY
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.HISTORY
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.HOME
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.ONBOARDING
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.QR_DETAIL
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.SCAN
import com.aayar94.qrscanner.presentation.generate.GenerateScreenContainer
import com.aayar94.qrscanner.presentation.history.HistoryScreenContainer
import com.aayar94.qrscanner.presentation.home.HomeScreenContainer
import com.aayar94.qrscanner.presentation.home.QrScannerScreen
import com.aayar94.qrscanner.presentation.onboarding.OnboardingScreenContainer
import com.aayar94.qrscanner.presentation.qr_detail.QRDetailScreenContainer
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(onFinishApp: () -> Unit) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController,
            startDestination = ONBOARDING
        ) {
            composable(HOME) {
                HomeScreenContainer(
                    onNavigateToQRDetail = {},
                    onNavigateToGenerate = {},
                    onNavigateToQRHistory = { navController.navigate(HISTORY) }
                )
            }
            composable(SCAN) { QrScannerScreen(result = {}) }
            composable(ONBOARDING) {
                OnboardingScreenContainer(onPermissionResult = { permissionResult ->
                    if (permissionResult) {
                        navController.navigate(HOME)
                    } else {
                        scope.launch {
                            val result = snackbarHostState
                                .showSnackbar(
                                    message = "Camera permission not granted",
                                    actionLabel = "Open Settings",
                                    // Defaults to SnackbarDuration.Short
                                    duration = SnackbarDuration.Indefinite
                                )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                            data =
                                                Uri.fromParts("package", context.packageName, null)
                                        }
                                    context.startActivity(intent)
                                }

                                SnackbarResult.Dismissed -> {
                                    onFinishApp.invoke()
                                }
                            }
                        }
                    }
                })
            }
            composable(HISTORY) {
                HistoryScreenContainer()
            }
            composable(GENERATE_By_CATEGORY) {
                GenerateScreenContainer()
            }
            composable(QR_DETAIL) {
                QRDetailScreenContainer()
            }
        }

    }

}
