package com.aayar94.qrscanner.data.local.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

object BitmapConverter {

    @TypeConverter
    @JvmStatic
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        return ByteArrayOutputStream().use { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
            stream.toByteArray()
        }
    }

    @TypeConverter
    @JvmStatic
    fun toBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}
