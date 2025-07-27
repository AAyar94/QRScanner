package com.aayar94.qrscanner.presentation.history

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.GrayBlack
import com.aayar94.qrscanner.core.theme.Yellow
import com.aayar94.qrscanner.domain.model.HistoryItem

@Composable
fun HistoryScreenContainer(
    onNavigateBack: () -> Unit,
    onNavigateSettings: () -> Unit,
    onNavigateDetail: (HistoryItem) -> Unit,
) {
    val vm: HistoryViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val uiEffect by vm.uiEffect.collectAsStateWithLifecycle(null)
    val uiAction = vm::onAction

    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            HistoryScreenContact.UiEffect.OnNavigateBack -> {
                onNavigateBack.invoke()
            }

            HistoryScreenContact.UiEffect.OnNavigateSettings -> {
                onNavigateSettings.invoke()
            }


            is HistoryScreenContact.UiEffect.OnNavigateDetail -> {
                onNavigateDetail.invoke((uiEffect as HistoryScreenContact.UiEffect.OnNavigateDetail).historyItem)
            }

            null -> {}
        }
    }

    HistoryScreen(uiState, uiEffect, uiAction)
}

@Composable
private fun HistoryScreen(
    uiState: HistoryScreenContact.UiState = HistoryScreenContact.UiState(),
    uiEffect: HistoryScreenContact.UiEffect? = null,
    uiAction: (HistoryScreenContact.UiAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray)
    ) {
        val state = rememberLazyListState()
        Column(
            modifier = Modifier
                .fillMaxSize()

                .padding(vertical = 24.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("History", color = Color.White)
                Box(
                    modifier = Modifier
                        .background(
                            Color.Black, shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            uiAction.invoke(HistoryScreenContact.UiAction.OnSettingsClicked)
                        }) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(16.dp),
                        painter = painterResource(R.drawable.ic_menu),
                        contentDescription = null,
                        tint = Yellow
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            val tab0Color by animateColorAsState(
                targetValue = if (uiState.selectedSection == 0) Yellow else GrayBlack,
                label = "Tab0Color"
            )
            val tab0Shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
                bottomStart = 16.dp
            )
            val tab1Color by animateColorAsState(
                targetValue = if (uiState.selectedSection == 1) Yellow else GrayBlack,
                label = "Tab1Color"
            )
            val tab1Shape =
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 0.dp
                )


            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(GrayBlack, RoundedCornerShape(24.dp))
            ) {
                TabRow(
                    uiState.selectedSection,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    containerColor = GrayBlack,
                    indicator = { null },
                    divider = { Spacer(modifier = Modifier.width(2.dp)) },
                ) {
                    Tab(
                        selected = uiState.selectedSection == 0,
                        onClick = {
                            uiAction.invoke(HistoryScreenContact.UiAction.OnSectionSelected(0))
                        },
                        modifier = Modifier
                            .background(
                                color = tab0Color,
                                shape = tab0Shape
                            )
                            .padding(8.dp),
                    ) {
                        Text(
                            text = "Scan", color = Color.White
                        )
                    }
                    Tab(
                        selected = uiState.selectedSection == 1, onClick = {
                            uiAction.invoke(HistoryScreenContact.UiAction.OnSectionSelected(1))
                        }, modifier = Modifier
                            .height(60.dp)
                            .background(
                                color = tab1Color,
                                shape = tab1Shape
                            )
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Create", color = Color.White
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            if (uiState.list.isEmpty()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("No History", color = Color.White)
                }
            } else {
                LazyColumn(
                    state = state, modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    itemsIndexed(uiState.list) { index, item ->
                        HistoryItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            historyItem = item,
                            onDeleteItem = {
                                uiAction.invoke(HistoryScreenContact.UiAction.OnDeleteHistoryItem(it))
                            },
                            onNavigateToDetails = {
                                uiAction.invoke(
                                    HistoryScreenContact.UiAction.OnNavigateToDetail(
                                        item
                                    )
                                )
                            })
                    }
                }
            }
        }
    }
}

/*@Preview
@Composable
private fun HistoryScreenPreview() {
    QRScannerTheme {
        HistoryScreen(
            uiState = HistoryScreenContact.UiState(
                selectedSection = 1, list = listOf(
                    HistoryItem(
                        id = 1,
                        "Tenda v12 Wifi",
                        QRCategory(3, R.string.category_wifi, R.drawable.ic_wifi),
                        LocalDateTime.now()
                    ),
                    HistoryItem(
                        "Ofis",
                        QRCategory(7, R.string.category_location, R.drawable.ic_location),
                        LocalDateTime.now()
                    ),
                    HistoryItem(
                        "Avukat Mail",
                        QRCategory(9, R.string.category_mail, R.drawable.ic_mail),
                        LocalDateTime.now()
                    ),


                    )
            ),
            uiEffect = null,
            uiAction = { }
        )
    }
}*/