package com.aayar94.qrscanner.domain.model

import android.graphics.Bitmap
import com.aayar94.qrscanner.data.local.database.HistoryItemEntity
import java.time.LocalDateTime

/** Domain model representing a scanned or generated QR code history entry. */
data class HistoryItem(
    val id: Int,
    /** Decoded QR code image. */
    val qrCode: Bitmap,
    /** Raw QR code string value (URL, vCard, etc.). */
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