package com.aayar94.qrscanner.data.local.database

import androidx.room.TypeConverter
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.domain.model.QRCategory
import java.time.LocalDateTime

object TypeConverters {

    // Inline list required — Room KSP cannot resolve domain-layer types at annotation processing time
    private val categories = listOf(
        QRCategory(1, R.string.category_text, R.drawable.ic_text),
        QRCategory(2, R.string.category_website, R.drawable.ic_internet),
        QRCategory(3, R.string.category_wifi, R.drawable.ic_wifi),
        QRCategory(4, R.string.category_calendar, R.drawable.ic_calendar),
        QRCategory(5, R.string.category_contacts, R.drawable.ic_contacts),
        QRCategory(6, R.string.category_organization, R.drawable.ic_organization),
        QRCategory(7, R.string.category_location, R.drawable.ic_location),
        QRCategory(8, R.string.category_whatsapp, R.drawable.ic_whatsapp),
        QRCategory(9, R.string.category_mail, R.drawable.ic_mail),
        QRCategory(10, R.string.category_instagram, R.drawable.ic_instagram),
        QRCategory(11, R.string.category_phone_number, R.drawable.ic_call),
    )


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