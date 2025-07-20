package com.aayar94.qrscanner.presentation.onboarding

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.core.theme.theme.QRScannerTheme

@Composable
fun OnboardingScreenContainer(modifier: Modifier = Modifier) {
    OnboardingScreen()
}

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val colors = listOf(
        Color(context.getColor(R.color.lime_green)),
        Color(context.getColor(R.color.cotton_blue))
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors)),
        contentAlignment = Alignment.Center
    ) {
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
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null, contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Go and enjoy our features for free and make your life easy with us.",
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(36.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.Black),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Let's Go", color = Color.White)
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    QRScannerTheme {
        OnboardingScreen()
    }
}