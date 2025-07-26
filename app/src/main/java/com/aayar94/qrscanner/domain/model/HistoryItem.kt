package com.aayar94.qrscanner.domain.model

import java.time.LocalDateTime

data class HistoryItem(
    val uriProxy: String,
    val category: QRCategory,
    val time: LocalDateTime
)