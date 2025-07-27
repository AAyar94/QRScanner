package com.aayar94.qrscanner.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase


const val QRDATABASE="qr_database"

@Database(
    entities = [HistoryItemEntity::class], version = 1, exportSchema = false
)
abstract class HistoryItemDatabase : RoomDatabase() {
    abstract fun historyItemDao(): HistoryItemDao
}