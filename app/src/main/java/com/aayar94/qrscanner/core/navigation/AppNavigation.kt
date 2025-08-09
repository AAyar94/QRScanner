package com.aayar94.qrscanner.core.navigation

import android.content.Intent
import android.graphics.Bitmap
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
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.GENERATED_QR_DETAIL
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.GENERATE_BY_CATEGORY
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.GENERATE_QR
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.HISTORY
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.HOME
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.ONBOARDING
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.QR_DETAIL
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.SCAN
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.SETTINGS
import com.aayar94.qrscanner.data.local.datastore.DataStoreRepository
import com.aayar94.qrscanner.domain.model.QRCategory
import com.aayar94.qrscanner.presentation.generate.GenerateScreenContainer
import com.aayar94.qrscanner.presentation.generate_by_category.GenerateByCategoryScreenContainer
import com.aayar94.qrscanner.presentation.generated_qr_detail.GeneratedQRDetailScreenContainer
import com.aayar94.qrscanner.presentation.history.HistoryScreenContainer
import com.aayar94.qrscanner.presentation.home.HomeScreenContainer
import com.aayar94.qrscanner.presentation.home.QrScannerScreen
import com.aayar94.qrscanner.presentation.onboarding.OnboardingScreenContainer
import com.aayar94.qrscanner.presentation.qr_detail.QRDetailScreenContainer
import com.aayar94.qrscanner.presentation.settings.SettingsScreenContainer
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(onFinishApp: () -> Unit, onboardingFinished: Boolean) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val dataStoreRepository = remember { DataStoreRepository(context = context) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController,
            startDestination = if (onboardingFinished) HOME else ONBOARDING
        ) {
            composable(HOME) {
                HomeScreenContainer(
                    onNavigateToQRDetail = { qrProxy ->
                        // TODO:detail e string proxy handle i ile gidecek
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "qrProxy",
                            qrProxy
                        )
                        scope.launch {
                            snackbarHostState.showSnackbar(qrProxy)
                        }
                        navController.navigate(QR_DETAIL)
                    },
                    onNavigateToGenerate = { navController.navigate(GENERATE_BY_CATEGORY) },
                    onNavigateToQRHistory = { navController.navigate(HISTORY) }
                )
            }
            composable(SCAN) { QrScannerScreen(result = {}) }
            composable(ONBOARDING) {
                OnboardingScreenContainer(
                    onPermissionResult = { permissionResult ->
                        if (permissionResult) {
                            navController.navigate(HOME)
                            coroutineScope.launch {
                                dataStoreRepository.saveOnboardingState(true)
                            }
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
                                                    Uri.fromParts(
                                                        "package",
                                                        context.packageName,
                                                        null
                                                    )
                                            }
                                        context.startActivity(intent)
                                    }

                                    SnackbarResult.Dismissed -> {
                                        onFinishApp.invoke()
                                    }
                                }
                            }
                        }
                    }, onPermissionNotGranted = {
                        onFinishApp.invoke()
                    })
            }
            composable(HISTORY) {
                HistoryScreenContainer(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateSettings = { navController.navigate(SETTINGS) },
                    onNavigateDetail = {}
                )
            }
            composable(GENERATE_BY_CATEGORY) {
                GenerateByCategoryScreenContainer(
                    onBackPressed = { navController.popBackStack() },
                    onCategorySelected = { categoryId ->
                        navController.navigate("$GENERATE_QR/$categoryId")
                    },
                    onSettingsSelected = {
                        navController.navigate(SETTINGS)
                    })
            }
            composable("$GENERATE_QR/{categoryId}") { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categoryId")
                GenerateScreenContainer(categoryId?.toIntOrNull(), navigateBack = {
                    navController.popBackStack()
                }, onSaveQR = { qrBitmap, qrCategory, qrProxy ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("qrBitmap", qrBitmap)
                    navController.currentBackStackEntry?.savedStateHandle?.set("qrProxy", qrProxy)
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "qrCategory",
                        qrCategory
                    )
                    navController.navigate(GENERATED_QR_DETAIL)
                })
            }
            composable(GENERATED_QR_DETAIL) {
                val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
                val qrBitmap = savedStateHandle?.get<Bitmap>("qrBitmap")
                val qrCategory = savedStateHandle?.get<QRCategory>("qrCategory")
                val qrProxy = savedStateHandle?.get<String>("qrProxy")
                qrCategory?.let { it1 ->
                    qrProxy?.let { qrProxy1 ->
                        GeneratedQRDetailScreenContainer(
                            qrBitmap,
                            it1,
                            qrProxy1,
                            onNavigateBack = { navController.popBackStack() },
                            onSaveQr = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "QR saved successfully",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        )
                    }
                }
            }
            composable(QR_DETAIL) {
                val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
                val qrProxy = savedStateHandle?.get<String>("qrProxy")
                if (qrProxy != null) {
                    QRDetailScreenContainer(qrProxy)
                }
            }
            composable(SETTINGS) {
                SettingsScreenContainer(
                    onBackPressed = { navController.popBackStack() }
                )
            }
        }
    }
}
