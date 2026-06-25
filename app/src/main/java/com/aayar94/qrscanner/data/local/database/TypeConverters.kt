package com.aayar94.qrscanner.data.local.database

import androidx.room.TypeConverter
import com.aayar94.qrscanner.domain.use_case.GetQRCategoryListUseCase
import java.time.LocalDateTime

object TypeConverters {

    private val categories = GetQRCategoryListUseCase.ALL_CATEGORIES


    @TypeConverter
    @JvmStatic
    fun fromCategory(category: QRCategory): Int = category.id

    @TypeConverter
    @JvmStatic
    fun toCategory(value: Int): QRCategory =
        categories.first { it.id == value }

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(time: LocalDateTime): String = time.toString()

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(value: String): LocalDateTime = LocalDateTime.parse(value)
}