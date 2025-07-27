package com.aayar94.qrscanner.data.local.datasource

import com.aayar94.qrscanner.data.local.database.HistoryItemDao
import com.aayar94.qrscanner.data.local.database.HistoryItemEntity
import com.aayar94.qrscanner.domain.datasource.ILocalDataSource
import javax.inject.Inject

class LocalDataSource @Inject constructor(val dao: HistoryItemDao) : ILocalDataSource {
    override suspend fun getAllScannedHistory(): List<HistoryItemEntity> {
        return dao.getScannedHistoryItems()
    }

    override suspend fun getAllCreatedHistory(): List<HistoryItemEntity> {
        return dao.getCreatedHistoryItems()
    }

    override suspend fun deleteHistoryItem(historyItemEntity: HistoryItemEntity) {
        return dao.deleteHistoryItem(historyItemEntity)
    }

    override suspend fun insertHistoryItem(historyItemEntity: HistoryItemEntity) {
        return dao.insertHistoryItem(historyItemEntity)
    }

}