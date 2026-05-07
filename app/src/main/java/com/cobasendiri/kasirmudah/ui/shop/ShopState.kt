package com.cobasendiri.kasirmudah.ui.shop

import com.cobasendiri.kasirmudah.model.Shop

data class ShopState(
    val shopName: String,
    val date: String,
    val totalAmount: Long,
    val filter: ShopFilter,
    val shopItemList: List<ShopItemState>,
    val isFloatingActionVisible: Boolean,
    val showEditItemDialog: Shop?,
    val showAddItemDialog: Boolean
)

data class ShopItemState(
    val shop: Shop,
    val count: Int
)