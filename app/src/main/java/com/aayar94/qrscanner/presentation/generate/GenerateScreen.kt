package com.aayar94.qrscanner.presentation.generate

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow
import com.aayar94.qrscanner.presentation.generate_by_category.GenerateByCategoryContact

@Composable
fun GenerateScreenContainer(categoryId: String? = null) {
    val vm: GenerateQRViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val uiEffect by vm.uiEffect.collectAsStateWithLifecycle(null)
    val uiAction = vm::onAction

    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            null -> {}
        }
    }

    GenerateScreen(uiState, uiEffect, uiAction)
}

@Composable
private fun GenerateScreen(
    uiState: GenerateQRContact.UiState,
    uiEffect: GenerateQRContact.UiEffect?,
    uiAction: (GenerateByCategoryContact.UiAction) -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(36.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(0.7f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 8.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = "Back",
                        tint = Yellow
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Generate Type",
                    color = Color.White,
                    fontSize = 24.sp
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight()
                        .background(Color.Black.copy(0.7f), shape = RoundedCornerShape(12.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val text = remember { mutableStateOf("") }
                        Icon(Icons.Default.TextFields, contentDescription = "Text", tint = Yellow)
                        Text(
                            text = "Type title",
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White
                        )
                        val state = rememberTextFieldState()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            BasicTextField(
                                state = state,
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .padding(vertical = 12.dp)
                                    .height(40.dp)
                                    .border(
                                        color = Color.White.copy(0.7f),
                                        shape = RoundedCornerShape(4.dp),
                                        width = 1.dp
                                    )
                                    .background(Color.Black.copy(0.7f), RoundedCornerShape(4.dp)),
                                enabled = true,
                                readOnly = false,
                                textStyle = MaterialTheme.typography.bodyMedium,
                            )
                            IconButton(
                                onClick = {},
                                modifier = Modifier
                                    .padding(12.dp)
                                    .size(30.dp, 30.dp)
                                    .wrapContentSize()
                                    .border(0.5.dp, Yellow, RoundedCornerShape(8.dp))
                            ) {
                                Icon(
                                    modifier = Modifier.size(30.dp, 30.dp),
                                    tint = Yellow,
                                    imageVector = Icons.Default.ContentPasteGo,
                                    contentDescription = null
                                )
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .background(Yellow, RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "Generate QR Code",
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun GenerateScreenPreview() {
    QRScannerTheme {
        GenerateScreen(
            uiState = GenerateQRContact.UiState(),
            uiEffect = null
        ) {}
    }
}