package com.aayar94.qrscanner.presentation.history

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.GrayBlack
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow
import com.aayar94.qrscanner.domain.model.HistoryItem
import com.aayar94.qrscanner.domain.model.QRCategory
import java.time.LocalDateTime

@Composable
fun HistoryScreenContainer(
    onNavigateBack: () -> Unit,
    onNavigateSettings: () -> Unit,
    onNavigateDetail: (HistoryItem) -> Unit
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
                            Color.Black,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {

                        }
                ) {
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
            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(GrayBlack, RoundedCornerShape(4.dp))
            ) {
                TabRow(
                    uiState.selectedSection,
                    modifier = Modifier
                        .background(GrayBlack, RoundedCornerShape(4.dp))
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
                                color = if (uiState.selectedSection == 0) Yellow else GrayBlack,
                                shape = if (uiState.selectedSection == 0) RoundedCornerShape(16.dp) else RoundedCornerShape(
                                    0.dp
                                )
                            )
                            .padding(8.dp),
                    ) {
                        Text(
                            text = "Scan",
                            color = Color.White
                        )
                    }
                    Tab(
                        selected = uiState.selectedSection == 1,
                        onClick = {
                            uiAction.invoke(HistoryScreenContact.UiAction.OnSectionSelected(0))
                        },
                        modifier = Modifier
                            .height(60.dp)
                            .background(
                                color = if (uiState.selectedSection == 1) Yellow else GrayBlack,
                                shape = if (uiState.selectedSection == 1) RoundedCornerShape(16.dp) else RoundedCornerShape(
                                    0.dp
                                )
                            )
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Create",
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
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
                            uiAction.invoke(HistoryScreenContact.UiAction.OnNavigateToDetail(item))
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HistoryScreenPreview() {
    QRScannerTheme {
        HistoryScreen(
            uiState = HistoryScreenContact.UiState(
                selectedSection = 1, list = listOf(
                    HistoryItem(
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
}