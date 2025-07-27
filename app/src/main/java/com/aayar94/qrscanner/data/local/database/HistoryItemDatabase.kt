package com.aayar94.qrscanner.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


const val QRDATABASE = "qr_database"

@Database(
    entities = [HistoryItemEntity::class], version = 1, exportSchema = false
)
@TypeConverters(
    com.aayar94.qrscanner.data.local.database.TypeConverters::class,
    BitmapConverter::class
)
abstract class HistoryItemDatabase : RoomDatabase() {
    abstract fun historyItemDao(): HistoryItemDao
}