package com.aayar94.qrscanner.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryItemDao {

    @Query("SELECT * FROM history_item_table WHERE isCreated =  0")
    suspend fun getScannedHistoryItems(): List<HistoryItemEntity>

    @Query("SELECT * FROM history_item_table WHERE isCreated =  1")
    suspend fun getCreatedHistoryItems(): List<HistoryItemEntity>

    @Delete
    suspend fun deleteHistoryItem(historyItemEntity: HistoryItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryItem(historyItemEntity: HistoryItemEntity)


}