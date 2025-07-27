package com.aayar94.qrscanner.data.local.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aayar94.qrscanner.domain.model.QRCategory
import java.time.LocalDateTime

@Entity(tableName = "history_item_table")
data class HistoryItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val qrCode: Bitmap,
    val uriProxy: String,
    val category: QRCategory,
    val time: LocalDateTime,
    val isCreated: Boolean
)