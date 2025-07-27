package com.aayar94.qrscanner.data.repository

import com.aayar94.qrscanner.data.local.database.HistoryItemEntity
import com.aayar94.qrscanner.data.local.datasource.LocalDataSource
import com.aayar94.qrscanner.domain.datasource.ILocalDataSource
import jakarta.inject.Inject

class QRScannerRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) : ILocalDataSource {
    override suspend fun getAllScannedHistory(): List<HistoryItemEntity> {
        return localDataSource.getAllScannedHistory()
    }

    override suspend fun getAllCreatedHistory(): List<HistoryItemEntity> {
        return localDataSource.getAllCreatedHistory()
    }

    override suspend fun deleteHistoryItem(historyItemEntity: HistoryItemEntity) {
        return localDataSource.deleteHistoryItem(historyItemEntity)
    }

    override suspend fun insertHistoryItem(historyItemEntity: HistoryItemEntity) {
        return localDataSource.insertHistoryItem(historyItemEntity)
    }
}