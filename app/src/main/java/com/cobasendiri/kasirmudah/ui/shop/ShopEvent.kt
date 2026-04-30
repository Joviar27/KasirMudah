package com.cobasendiri.kasirmudah.ui.shop

interface ShopEvent {
    data class OnSearch(val searchQuery: String): ShopEvent
    data object OnFinish: ShopEvent
    data class OnCountChange(val changedAmount: Long): ShopEvent
}