package com.cobasendiri.kasirmudah.data

import com.cobasendiri.kasirmudah.data.room.CartDao
import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import com.cobasendiri.kasirmudah.domain.repository.ICartRepository
import com.cobasendiri.kasirmudah.util.DataMapper.mapListToDomain
import com.cobasendiri.kasirmudah.util.mapCatchFlow
import com.cobasendiri.kasirmudah.util.runCatchSuspending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepository(
    private val cartDao: CartDao
): ICartRepository {
    companion object {
        @Volatile
        private var instance: ICartRepository? = null

        fun getInstance(cartDao: CartDao): ICartRepository {
            return instance ?: synchronized(this) {
                instance ?: CartRepository(cartDao)
                    .also { instance = it }
            }
        }
    }

    override fun getAllCartProduct(): Flow<Result<List<ProductInfo>>>{
        return cartDao.getAllCartProducts()
            .map { it.mapListToDomain() }
            .mapCatchFlow()
    }

    override fun getTotalCartAmount(): Flow<Result<Double?>>{
        return cartDao.getTotalCartAmount().mapCatchFlow()
    }

    override suspend fun addOrIncrementProduct(productId: String): Result<Unit>{
        return runCatchSuspending {
            cartDao.addOrIncrementProduct(productId)
        }
    }

    override suspend fun decrementProduct(productId: String): Result<Unit>{
        return runCatchSuspending {
            cartDao.decrementProduct(productId)
        }
    }
}