package com.cobasendiri.kasirmudah.data

import com.cobasendiri.kasirmudah.data.room.ProductDao
import com.cobasendiri.kasirmudah.model.Product
import com.cobasendiri.kasirmudah.model.ProductDraft
import com.cobasendiri.kasirmudah.model.ProductInfo
import com.cobasendiri.kasirmudah.util.DataMapper.mapListToDomain
import com.cobasendiri.kasirmudah.util.DataMapper.mapToEntity
import com.cobasendiri.kasirmudah.util.mapCatchFlow
import com.cobasendiri.kasirmudah.util.runCatchSuspending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val productDao: ProductDao
) {

    fun getAllProducts(): Flow<Result<List<ProductInfo>>> {
        return productDao.getAllProducts()
            .map { it.mapListToDomain() }
            .mapCatchFlow()
    }

    suspend fun addNewProduct(newProduct: Product): Result<Unit>{
        return runCatchSuspending {
            productDao.addProduct(newProduct.mapToEntity())
        }
    }

    suspend fun updateProduct(productDraft: ProductDraft): Result<Unit>{
        return runCatchSuspending {
            productDao.updateProduct(productDraft.mapToEntity())
        }
    }

    suspend fun deleteProduct(productId: String): Result<Unit>{
        return runCatchSuspending {
            productDao.deleteProduct(productId)
        }
    }
}

