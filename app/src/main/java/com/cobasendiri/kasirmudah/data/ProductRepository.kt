package com.cobasendiri.kasirmudah.data

import com.cobasendiri.kasirmudah.data.room.ProductDao
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.model.ProductDraft
import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository
import com.cobasendiri.kasirmudah.util.DataMapper.mapListToDomain
import com.cobasendiri.kasirmudah.util.DataMapper.mapToEntity
import com.cobasendiri.kasirmudah.util.mapCatchFlow
import com.cobasendiri.kasirmudah.util.runCatchSuspending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val productDao: ProductDao
): IProductRepository {

    companion object {
        @Volatile
        private var instance: IProductRepository? = null

        fun getInstance(productDao: ProductDao): IProductRepository {
            return instance ?: synchronized(this) {
                instance ?: ProductRepository(productDao)
                    .also { instance = it }
            }
        }
    }

    override fun getAllProducts(): Flow<Result<List<ProductInfo>>> {
        return productDao.getAllProducts()
            .map { it.mapListToDomain() }
            .mapCatchFlow()
    }

    override suspend fun addNewProduct(newProduct: Product): Result<Unit>{
        return runCatchSuspending {
            productDao.addProduct(newProduct.mapToEntity())
        }
    }

    override suspend fun updateProduct(productDraft: ProductDraft): Result<Unit>{
        return runCatchSuspending {
            productDao.updateProduct(productDraft.mapToEntity())
        }
    }

    override suspend fun deleteProduct(productId: String): Result<Unit>{
        return runCatchSuspending {
            productDao.deleteProduct(productId)
        }
    }
}

