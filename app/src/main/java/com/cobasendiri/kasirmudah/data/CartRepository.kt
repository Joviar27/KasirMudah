package com.cobasendiri.kasirmudah.data

import com.cobasendiri.kasirmudah.data.room.CartDao
import com.cobasendiri.kasirmudah.model.ProductInfo
import com.cobasendiri.kasirmudah.util.DataMapper.mapListToDomain
import com.cobasendiri.kasirmudah.util.mapCatchFlow
import com.cobasendiri.kasirmudah.util.runCatchSuspending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepository(
    private val cartDao: CartDao
) {

    fun getAllCardProduct(): Flow<Result<List<ProductInfo>>>{
        return cartDao.getAllCartProducts()
            .map { it.mapListToDomain() }
            .mapCatchFlow()
    }

    fun getTotalCartAmount(): Flow<Result<Double?>>{
        return cartDao.getTotalCartAmount().mapCatchFlow()
    }

    suspend fun addOrIncrementProduct(productId: String): Result<Unit>{
        return runCatchSuspending {
            cartDao.addOrIncrementProduct(productId)
        }
    }

    suspend fun decrementProduct(productId: String): Result<Unit>{
        return runCatchSuspending {
            cartDao.decrementProduct(productId)
        }
    }
}