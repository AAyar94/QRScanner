package com.aayar94.qrscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.aayar94.qrscanner.core.navigation.AppNavigation
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            mainViewModel.onboardingFinished.value == null
        }
        enableEdgeToEdge()

        setContent {
            val onboardingFinished by mainViewModel.onboardingFinished.collectAsState()

            if (onboardingFinished != null) {
                QRScannerTheme {
                    AppNavigation(
                        onFinishApp = {
                            finish()
                        },
                        onboardingFinished = onboardingFinished == true
                    )
                }
            }
        }
    }
}