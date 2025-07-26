package com.aayar94.qrscanner.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class QRCategory(
    val id: Int,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
) : Parcelable