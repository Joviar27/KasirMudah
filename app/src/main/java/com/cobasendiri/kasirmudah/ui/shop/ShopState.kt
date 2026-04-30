package com.cobasendiri.kasirmudah.ui.shop

import com.cobasendiri.kasirmudah.model.Shop

data class ShopState(
    val shopName: String,
    val date: String,
    val totalAmount: String,
    val shopItemList: List<Shop>
)