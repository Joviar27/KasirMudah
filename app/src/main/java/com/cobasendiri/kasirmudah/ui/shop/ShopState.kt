package com.cobasendiri.kasirmudah.ui.shop

import com.cobasendiri.kasirmudah.model.Shop

data class ShopState(
    val shopName: String,
    val date: String,
    val totalAmount: Long,
    val shopItemList: List<Shop>,
    val isFloatingActionVisible: Boolean
)