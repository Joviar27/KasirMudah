package com.cobasendiri.kasirmudah.model

import androidx.compose.ui.graphics.Color

data class Shop(
    val id: String,

    val name: String,

    //Formatted with currency and decimal: eg: Rp 15.000,00
    val displayPrice: String,

    //Raw value
    val rawPrice: Long,

    val colorCode: Color,

    val count: Int
)