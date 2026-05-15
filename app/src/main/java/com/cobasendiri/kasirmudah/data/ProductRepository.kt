package com.cobasendiri.kasirmudah.data

import android.util.Log
import com.cobasendiri.kasirmudah.data.room.CartDao
import com.cobasendiri.kasirmudah.data.room.ProductDao
import com.cobasendiri.kasirmudah.model.ProductInfo
import com.cobasendiri.kasirmudah.util.DataMapper.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val productDao: ProductDao,
    private val cartDao: CartDao
) {

    fun getAllProducts(): Flow<Result<List<ProductInfo>>> {
        return productDao.getAllProducts()
            .map { productResult ->
                val productInfoList = productResult.map {
                    it.mapToDomain()
                }
                Result.Success(productInfoList)
            }.catch {
                Log.e("${this@ProductRepository.javaClass.simpleName}","message: ${it.message}")
                Result.Error(it.message.toString())
            }
    }
}