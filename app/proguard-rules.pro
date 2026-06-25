# Keep line numbers for crash stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class jakarta.inject.** { *; }
-keepclasseswithmembers class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}

# Room
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }
-keepclassmembers class * extends androidx.room.TypeConverter { *; }

# ML Kit Barcode Scanning
-keep class com.google.mlkit.** { *; }
-keep class com.google.android.gms.internal.mlkit_vision_barcode.** { *; }

# DataStore
-keep class androidx.datastore.** { *; }

# QRSmith
-keep class com.akansh.qrsmith.** { *; }

# App models (Parcelable, Room entities)
-keep class com.aayar94.qrscanner.domain.model.** { *; }
-keep class com.aayar94.qrscanner.data.local.database.** { *; }

# Kotlin coroutines
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Kotlin serialization / Parcelize
-keepclassmembers @kotlinx.parcelize.Parcelize class * { *; }
