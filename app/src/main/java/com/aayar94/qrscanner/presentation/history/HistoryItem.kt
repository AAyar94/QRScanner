package com.aayar94.qrscanner.presentation.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.core.theme.GrayBlack
import com.aayar94.qrscanner.core.theme.Yellow
import com.aayar94.qrscanner.domain.model.HistoryItem
import java.time.format.DateTimeFormatter

@Composable
fun HistoryItem(
    modifier: Modifier = Modifier,
    historyItem: HistoryItem,
    onDeleteItem: (HistoryItem) -> Unit,
    onNavigateToDetails: () -> Unit
) {

    Card(
        modifier = modifier
            .padding(vertical = 8.dp)
            .background(GrayBlack, shape = RoundedCornerShape(12.dp))
            .clickable { onNavigateToDetails.invoke() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = GrayBlack),
        elevation = CardDefaults.cardElevation(10.dp, 5.dp)
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(historyItem.category.icon),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(historyItem.uriProxy, color = Color.White)
                Text(stringResource(historyItem.category.name), color = Color.White)
            }
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
                IconButton(
                    onClick = { onDeleteItem.invoke(historyItem) },
                    modifier = Modifier,
                    enabled = true
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = null,
                        tint = Yellow
                    )
                }
                Text(
                    "${historyItem.time.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))}",
                    color = Color.White
                )
            }
        }
    }
}

/*@Preview
@Composable
private fun HistoryItemPreview() {
    QRScannerTheme {
        HistoryItem(
            modifier = Modifier.fillMaxWidth(),
            historyItem = HistoryItem(
                "Tenda v12 Wifi",
                QRCategory(3, R.string.category_wifi, R.drawable.ic_wifi),
                LocalDateTime.now()
            ),
            onDeleteItem = {},
            onNavigateToDetails = {}
        )
    }
}*/