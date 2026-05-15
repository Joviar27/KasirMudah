package com.cobasendiri.kasirmudah.ui.shop

import com.cobasendiri.kasirmudah.model.Product

data class ShopState(
    val shopName: String,
    val date: String,
    val totalAmount: Long,
    val filter: ShopFilter,
    val shopItemList: List<ProductItemState>,
    val isFloatingActionVisible: Boolean,
    val showEditProductDialog: Product?,
    val showAddProductDialog: Boolean,
    val showConfirmDeleteDialog: String?
)

data class ProductItemState(
    val product: Product,
    val count: Int
)