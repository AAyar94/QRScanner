package com.aayar94.qrscanner.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

/** Represents a supported QR code category (e.g. WiFi, Contact, Website). */
@Parcelize
data class QRCategory(
    val id: Int,
    /** String resource for the category display name. */
    @StringRes val name: Int,
    /** Drawable resource for the category icon. */
    @DrawableRes val icon: Int,
) : Parcelable