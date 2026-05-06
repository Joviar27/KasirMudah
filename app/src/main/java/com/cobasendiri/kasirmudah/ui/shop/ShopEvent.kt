package com.cobasendiri.kasirmudah.ui.shop

import androidx.compose.ui.graphics.Color

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
}