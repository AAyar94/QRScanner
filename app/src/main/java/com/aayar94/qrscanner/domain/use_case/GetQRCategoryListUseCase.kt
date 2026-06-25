package com.aayar94.qrscanner.domain.use_case

import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.domain.model.QRCategory
import javax.inject.Inject

/** Returns the full list of supported QR code categories. */
class GetQRCategoryListUseCase @Inject constructor() {

    fun getQRCategoriesList(): List<QRCategory> = ALL_CATEGORIES

    companion object {
        val ALL_CATEGORIES: List<QRCategory> = listOf(
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
    }
}