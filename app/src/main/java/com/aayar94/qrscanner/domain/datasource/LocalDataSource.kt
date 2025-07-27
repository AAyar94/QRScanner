package com.aayar94.qrscanner.domain.datasource

import com.aayar94.qrscanner.data.local.database.HistoryItemEntity

interface ILocalDataSource {

    suspend fun getAllScannedHistory(): List<HistoryItemEntity>
    suspend fun getAllCreatedHistory(): List<HistoryItemEntity>

    suspend fun deleteHistoryItem(historyItemEntity: HistoryItemEntity)
    suspend fun insertHistoryItem(historyItemEntity: HistoryItemEntity)

}