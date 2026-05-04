package com.cobasendiri.kasirmudah.ui.shop

interface ShopEvent {
    data class OnSearch(val searchQuery: String): ShopEvent

    data object OnFinish: ShopEvent

    data class OnItemIncrease(
        val itemId: String,
        val itemPrice: Long
    ): ShopEvent

    data class OnItemDecrease(
        val itemId: String,
        val itemPrice: Long
    ): ShopEvent
}