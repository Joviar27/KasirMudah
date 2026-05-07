package com.cobasendiri.kasirmudah.ui.shop

import androidx.compose.ui.graphics.Color
import com.cobasendiri.kasirmudah.model.Shop
import com.cobasendiri.kasirmudah.model.ShopDraft

interface ShopEvent {
    data class OnSearch(val searchQuery: String): ShopEvent

    data object OnFinish: ShopEvent

    data object OnReset: ShopEvent

    data class OnItemIncrease(val itemId: String): ShopEvent

    data class OnItemDecrease(val itemId: String): ShopEvent

    data class OnItemNewColor(
        val itemId: String,
        val newColor: Color
    ): ShopEvent

    data class OnFilterChange(
        val newFilter: ShopFilter
    ): ShopEvent

    data object OnShowAddItemDialog: ShopEvent

    data class OnShowEditItemDialog(
        val shop: Shop
    ): ShopEvent

    data object OnDismissItemDialog: ShopEvent

    data class OnNewShopItem(
        val newShop: ShopDraft
    ): ShopEvent

    data class OnUpdateShopItem(
        val updatedShop: ShopDraft
    ): ShopEvent
}