package com.cobasendiri.kasirmudah.ui.shop

import com.cobasendiri.kasirmudah.model.Shop

data class ShopState(
    val shopName: String,
    val date: String,
    val totalAmount: Long,
    val shopItemList: List<ShopItemState>,
    val isFloatingActionVisible: Boolean
)

data class ShopItemState(
    val shop: Shop,
    val count: Int
)