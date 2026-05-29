package com.cobasendiri.kasirmudah.data

import com.cobasendiri.kasirmudah.data.room.CartDao
import com.cobasendiri.kasirmudah.data.util.CoroutineMapper.mapExceptionFlow
import com.cobasendiri.kasirmudah.data.util.CoroutineMapper.runMapExceptionSuspending
import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import com.cobasendiri.kasirmudah.domain.repository.ICartRepository
import com.cobasendiri.kasirmudah.data.util.DataMapper.mapListToDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CartRepository(
    private val cartDao: CartDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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

    override fun getAllCartProduct(seachQuery: String): Flow<List<ProductInfo>?>{
        return cartDao.getAllCartProducts()
            .mapExceptionFlow{ products ->
                val filtered = if(seachQuery.isNotEmpty()){
                    products.filter { it.productEntity.name.contains(seachQuery, true) }
                }else {
                    products
                }
                filtered.mapListToDomain()
            }.flowOn(ioDispatcher)
    }

    override fun getTotalCartAmount(): Flow<Double?>{
        return cartDao.getTotalCartAmount().mapExceptionFlow()
            .flowOn(ioDispatcher)
    }

    override suspend fun addOrIncrementProduct(
        productId: String
    ) = withContext(ioDispatcher) {
        runMapExceptionSuspending {
            cartDao.addOrIncrementProduct(productId)
        }
    }

    override suspend fun decrementProduct(
        productId: String
    ) = withContext(ioDispatcher) {
        runMapExceptionSuspending {
            cartDao.decrementProduct(productId)
        }
    }

    override suspend fun clearCart() =
        withContext(ioDispatcher) {
            runMapExceptionSuspending {
                cartDao.deleteAllCart()
            }
        }
}