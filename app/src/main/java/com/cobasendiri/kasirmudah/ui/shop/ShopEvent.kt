package com.cobasendiri.kasirmudah.ui.shop

import androidx.compose.ui.graphics.Color
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.model.ProductDraft

interface ShopEvent {
    data class OnSearch(val searchQuery: String): ShopEvent

    data object OnFinish: ShopEvent

    data object OnReset: ShopEvent

    data class OnIncreaseProduct(val itemId: String): ShopEvent

    data class OnDecreaseProduct(val itemId: String): ShopEvent

    data class OnUpdateProductColor(
        val productId: String,
        val newColor: Long
    ): ShopEvent

    data class OnFilterChange(
        val newFilter: ShopFilter
    ): ShopEvent

    data object OnShowAddProductDialog: ShopEvent

    data class OnShowEditProductDialog(
        val product: Product
    ): ShopEvent

    data object OnDismissProductDetailDialog: ShopEvent

    data class OnShowConfirmDeleteDialog(
        val itemId: String
    ): ShopEvent

    data object OnDismissConfirmDeleteDialog: ShopEvent

    data class OnNewProduct(
        val newProduct: ProductDraft
    ): ShopEvent

    data class OnUpdateProduct(
        val updatedProduct: ProductDraft
    ): ShopEvent

    data class OnDeleteProduct(
        val productId: String
    ): ShopEvent
}