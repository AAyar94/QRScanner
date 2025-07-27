package com.aayar94.qrscanner.core.di

import android.content.Context
import androidx.room.Room
import com.aayar94.qrscanner.data.local.database.HistoryItemDao
import com.aayar94.qrscanner.data.local.database.HistoryItemDatabase
import com.aayar94.qrscanner.data.local.database.QRDATABASE
import com.aayar94.qrscanner.data.local.datasource.LocalDataSource
import com.aayar94.qrscanner.data.repository.QRScannerRepository
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@InstallIn(SingletonComponent::class)
object QRScannerModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        HistoryItemDatabase::class.java,
        QRDATABASE
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: HistoryItemDatabase) = database.historyItemDao()

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: HistoryItemDao): LocalDataSource {
        return LocalDataSource(dao)
    }

    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: LocalDataSource,
    ): QRScannerRepository {
        return QRScannerRepository(localDataSource = localDataSource)
    }


}