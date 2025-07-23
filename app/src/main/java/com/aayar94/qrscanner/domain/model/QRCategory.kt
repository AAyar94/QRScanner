package com.aayar94.qrscanner.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class QRCategory(
    val id: Int,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
) {
}