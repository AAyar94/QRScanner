package com.aayar94.qrscanner.domain.model

import android.graphics.Bitmap
import com.aayar94.qrscanner.data.local.database.HistoryItemEntity
import java.time.LocalDateTime

data class HistoryItem(
    val id: Int,
    val qrCode: Bitmap,
    val uriProxy: String,
    val category: QRCategory,
    val time: LocalDateTime
)


fun HistoryItemEntity.toHistoryItem() = HistoryItem(
    id = id,
    qrCode = qrCode,
    uriProxy = uriProxy,
    category = category,
    time = time
)