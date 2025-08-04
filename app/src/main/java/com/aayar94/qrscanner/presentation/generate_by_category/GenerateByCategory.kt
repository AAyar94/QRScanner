package com.aayar94.qrscanner.presentation.generate_by_category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.core.component.PageHeader
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.domain.model.QRCategory

@Composable
fun GenerateByCategoryScreenContainer(
    onBackPressed: () -> Unit,
    onCategorySelected: (Int) -> Unit,
    onSettingsSelected: () -> Unit
) {
    val vm: GenerateByCategoryViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val uiEffect by vm.uiEffect.collectAsStateWithLifecycle(null)
    val uiAction = vm::onAction

    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            is GenerateByCategoryContact.UiEffect.OnNavigateToGenerateQR -> {
                onCategorySelected.invoke((uiEffect as GenerateByCategoryContact.UiEffect.OnNavigateToGenerateQR).categoryId)
            }

            null -> {}
            GenerateByCategoryContact.UiEffect.OnSettingsSelected -> onSettingsSelected.invoke()
            GenerateByCategoryContact.UiEffect.OnNavigateBack -> {
                onBackPressed.invoke()
            }
        }
    }

    GenerateByCategoryScreen(uiState, uiEffect, uiAction)
}

@Composable
private fun GenerateByCategoryScreen(
    uiState: GenerateByCategoryContact.UiState,
    uiEffect: GenerateByCategoryContact.UiEffect?,
    uiAction: (GenerateByCategoryContact.UiAction) -> Unit
) {
    val state = rememberLazyGridState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageHeader(
                modifier = Modifier.fillMaxWidth(),
                onLeftAction = { uiAction.invoke(GenerateByCategoryContact.UiAction.OnBackPressed) },
                leftActionIcon = Icons.AutoMirrored.Outlined.ArrowBack,
                leftActionDescription = "Back",
                title = "Generate QR",
                onRightAction = {
                    uiAction.invoke(GenerateByCategoryContact.UiAction.OnSettingsSelected)
                },
                rightActionDescription = "Settings",
                rightActionIcon = Icons.Outlined.Settings
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.weight(1f),
                state = state,
                contentPadding = PaddingValues(vertical = 6.dp, horizontal = 0.dp),
            ) {
                itemsIndexed(uiState.categoryList) { index, item ->
                    CategoryItem(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp),
                        category = item,
                        onCategorySelect = {
                            uiAction.invoke(
                                GenerateByCategoryContact.UiAction.OnCategorySelected(it)
                            )
                        })
                }

            }
        }
    }
}

@Preview
@Composable
private fun GenerateByCategoryScreenPreview() {
    QRScannerTheme {
        val uiState = GenerateByCategoryContact.UiState(
            categoryList = listOf(
                QRCategory(1, R.string.category_text, R.drawable.ic_text),
                QRCategory(2, R.string.category_website, R.drawable.ic_internet),
                QRCategory(3, R.string.category_wifi, R.drawable.ic_wifi),
                QRCategory(4, R.string.category_calendar, R.drawable.ic_calendar),
                QRCategory(5, R.string.category_contacts, R.drawable.ic_contacts),
                QRCategory(6, R.string.category_organization, R.drawable.ic_organization),
                QRCategory(7, R.string.category_location, R.drawable.ic_location),
                QRCategory(8, R.string.category_whatsapp, R.drawable.ic_whatsapp),
                QRCategory(9, R.string.category_mail, R.drawable.ic_mail),
                QRCategory(10, R.string.category_instagram, R.drawable.ic_instagram),
                QRCategory(11, R.string.category_phone_number, R.drawable.ic_call),
            )
        )
        GenerateByCategoryScreen(uiState, null) {}
    }
}