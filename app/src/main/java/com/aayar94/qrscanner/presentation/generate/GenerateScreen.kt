package com.aayar94.qrscanner.presentation.generate

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.core.component.PageHeader
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow
import com.aayar94.qrscanner.domain.model.QRCategory


@Composable
fun GenerateScreenContainer(
    categoryId: Int? = null,
    navigateBack: () -> Unit,
    onSaveQR: (Bitmap, QRCategory, String) -> Unit
) {
    val vm: GenerateQRViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val uiEffect by vm.uiEffect.collectAsStateWithLifecycle(null)
    val uiAction = vm::onAction

    LaunchedEffect(categoryId) {
        categoryId?.let {
            uiAction.invoke(
                GenerateQRContact.UiAction.CategoryPickedInitalizeUI(
                    it
                )
            )
        }
    }

    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            null -> {}
            GenerateQRContact.UiEffect.OnNavigateBack -> {
                navigateBack.invoke()
            }

            is GenerateQRContact.UiEffect.OnNavigateGeneratedQRDetail -> {
                onSaveQR.invoke(
                    (uiEffect as GenerateQRContact.UiEffect.OnNavigateGeneratedQRDetail).qrCode,
                    (uiEffect as GenerateQRContact.UiEffect.OnNavigateGeneratedQRDetail).category,
                    (uiEffect as GenerateQRContact.UiEffect.OnNavigateGeneratedQRDetail).qrProxy
                )
            }
        }
    }

    GenerateScreen(uiState, uiEffect, uiAction)
}

@Composable
private fun GenerateScreen(
    uiState: GenerateQRContact.UiState,
    uiEffect: GenerateQRContact.UiEffect?,
    uiAction: (GenerateQRContact.UiAction) -> Unit
) {
    val clipboardManager = LocalClipboard.current
    val text = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Box(
        Modifier
            .fillMaxSize()
            .background(Gray)
            .consumeWindowInsets(WindowInsets.ime)
            .padding(WindowInsets.ime.asPaddingValues())
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
                PageHeader(
                    modifier = Modifier.fillMaxWidth(),
                    onLeftAction = { uiAction.invoke(GenerateQRContact.UiAction.OnBackPressed) },
                    leftActionIcon = Icons.AutoMirrored.Outlined.ArrowBack,
                    leftActionDescription = "Back",
                    title = "Generate Type",
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.Center
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
                            .wrapContentHeight()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        uiState.selectedCategory?.icon?.let {
                            Image(
                                painter = painterResource(it),
                                contentDescription = "Text",
                            )
                        }
                        Text(
                            text = "Type Web adress",
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White
                        )
                        uiState.selectedCategory?.name?.let {
                            GenerateQRTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                title = stringResource(it),
                                isPasteEnabled = true,
                                afterTextChanged = {
                                    uiAction.invoke(GenerateQRContact.UiAction.OnPasteClicked(it))
                                })
                        }
                        if (uiState.selectedCategory?.id == 3) {
                            Text(
                                text = "Type title",
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextField(
                                    value = uiState.uriText ?: "",
                                    onValueChange = { newValue: String ->
                                        uiAction.invoke(
                                            GenerateQRContact.UiAction.OnUpdateUriText(
                                                uriText = newValue
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .padding(vertical = 12.dp)
                                        .wrapContentHeight()
                                        .border(
                                            color = Color.White.copy(0.7f),
                                            shape = RoundedCornerShape(4.dp),
                                            width = 1.dp
                                        )
                                        .background(
                                            Color.Black.copy(0.7f), RoundedCornerShape(4.dp)
                                        ),
                                    colors = TextFieldDefaults.colors(
                                        focusedTextColor = Color.White,
                                        focusedContainerColor = Color.Black,
                                        unfocusedTextColor = Color.White,
                                        unfocusedContainerColor = Color.Black,
                                        disabledTextColor = Color.White,
                                    ),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            keyboardController?.hide()
                                        })
                                )
                                IconButton(
                                    onClick = {
                                        val clipText = clipboardManager.nativeClipboard.text
                                        if (clipText != null) {
                                            uiAction.invoke(
                                                GenerateQRContact.UiAction.OnPasteClicked(
                                                    clipText.toString()
                                                )
                                            )
                                        }
                                    },
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
                        }
                        Spacer(Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .background(Yellow, RoundedCornerShape(4.dp))
                                .clickable {
                                    uiAction.invoke(
                                        GenerateQRContact.UiAction.OnGeneraQrCode(
                                            qrProxy = uiState.uriText.toString(),
                                            category = uiState.selectedCategory!!,
                                            bgColor = ContextCompat.getColor(
                                                context, R.color.white
                                            ),
                                            fgColor = ContextCompat.getColor(context, R.color.black)
                                        )
                                    )
                                }, contentAlignment = Alignment.Center
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
            uiState = GenerateQRContact.UiState(
                selectedCategory = QRCategory(2, R.string.category_website, R.drawable.ic_internet)
            ), uiEffect = null, uiAction = {})
    }
}