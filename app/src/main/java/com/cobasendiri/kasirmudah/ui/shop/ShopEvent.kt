package com.cobasendiri.kasirmudah.ui.shop

interface ShopEvent {
    data class OnSearch(val searchQuery: String): ShopEvent

    data object OnFinish: ShopEvent

    data object OnReset: ShopEvent

    data class OnItemIncrease(val itemId: String): ShopEvent

    data class OnItemDecrease(val itemId: String): ShopEvent
}