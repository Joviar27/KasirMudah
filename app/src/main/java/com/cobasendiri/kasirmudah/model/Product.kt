package com.cobasendiri.kasirmudah.model

import androidx.compose.ui.graphics.Color

data class Product(
    val id: String,

    val name: String,

    val price: Long,

    val colorCode: Color
)