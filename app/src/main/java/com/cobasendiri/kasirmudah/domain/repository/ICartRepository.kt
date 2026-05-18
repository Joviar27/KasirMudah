package com.cobasendiri.kasirmudah.domain.repository

import com.cobasendiri.kasirmudah.data.Result
import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import kotlinx.coroutines.flow.Flow

interface ICartRepository {

    fun getAllCartProduct(): Flow<Result<List<ProductInfo>>>

    fun getTotalCartAmount(): Flow<Result<Double?>>

    suspend fun addOrIncrementProduct(productId: String): Result<Unit>

    suspend fun decrementProduct(productId: String): Result<Unit>
}