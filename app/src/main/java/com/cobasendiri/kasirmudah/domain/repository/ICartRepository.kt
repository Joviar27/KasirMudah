package com.cobasendiri.kasirmudah.domain.repository

import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo
import kotlinx.coroutines.flow.Flow

interface ICartRepository {

    fun getAllCartProduct(searchQuery: String): Flow<List<ProductInfo>?>

    fun getTotalCartAmount(): Flow<Long?>

    suspend fun addOrIncrementProduct(productId: String)

    suspend fun decrementProduct(productId: String)

    suspend fun clearCart()

    suspend fun getProductsTotal(): List<TransactionItemInfo>
}