package com.aayar94.qrscanner.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.ONBOARDING
import com.aayar94.qrscanner.core.navigation.NavigationRoutes.SCAN
import com.aayar94.qrscanner.presentation.home.QrScannerScreen
import com.aayar94.qrscanner.presentation.onboarding.OnboardingScreenContainer

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ONBOARDING) {
        composable(SCAN) { QrScannerScreen() }
        composable(ONBOARDING) { OnboardingScreenContainer() }
    }
}
