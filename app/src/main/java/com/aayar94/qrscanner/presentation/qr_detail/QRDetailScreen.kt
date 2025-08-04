package com.aayar94.qrscanner.presentation.qr_detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.aayar94.qrscanner.core.component.PageHeader
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow


@Composable
fun QRDetailScreenContainer(qrProxy: String) {
    QRDetailScreen(qrProxy)
}

@Composable
private fun QRDetailScreen(qrProxy: String) {
    val context = LocalContext.current
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
            PageHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                onLeftAction = {},
                leftActionIcon = Icons.AutoMirrored.Default.ArrowBackIos,
                leftActionDescription = "Back",
                title = "Result"
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(0.7f), RoundedCornerShape(12.dp))
                    .padding(vertical = 24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            Modifier
                                .size(64.dp)
                                .border(1.dp, Yellow, RoundedCornerShape(12.dp))
                        ) {
                            Icon(
                                modifier = Modifier.size(64.dp),
                                imageVector = Icons.Default.QrCode2,
                                contentDescription = "QR",
                                tint = Color.White,
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("QR Type", color = Color.White)
                            Text("16 Dec 2024 10:10", color = Color.White)
                        }
                    }
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Gray,
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            modifier = Modifier.clickable {
                                val url = qrProxy
                                val i = Intent(Intent.ACTION_VIEW)
                                i.setData(url.toUri())
                                context.startActivity(i)
                            },
                            text = qrProxy,
                            color = Color.White,
                            textDecoration = TextDecoration.Underline
                        )
                        Text("Save QR Code", color = Yellow)
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Yellow, shape = RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share")

                    }
                    Text("Share", color = Color.White)
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Yellow, shape = RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy")

                    }
                    Text("Copy", color = Color.White)
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Yellow, shape = RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.SaveAlt, contentDescription = "Save")

                    }
                    Text("Save", color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
private fun QRDetailScreenPreview() {
    QRScannerTheme {
        QRDetailScreen("Hello link")
    }
}