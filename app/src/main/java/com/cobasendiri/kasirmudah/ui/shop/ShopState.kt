package com.cobasendiri.kasirmudah.ui.shop

import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage

data class ShopState(
    val shopName: String = "",
    val date: Long = 0L,
    val totalAmount: Long = 0L,
    val filter: ShopFilter = ShopFilter.FILTER_ALL,
    val shopItemList: List<ProductInfo> = listOf(),
    val searchQuery: String = "",
    val uiMessage: UiMessage? = null,
    val isFloatingActionVisible: Boolean = false,
    val showEditProductDialog: Product? = null,
    val showAddProductDialog: Boolean = false,
    val showConfirmDeleteDialog: String? = null,
    val showEmptyListView: Boolean = false
)