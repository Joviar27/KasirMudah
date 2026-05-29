package com.cobasendiri.kasirmudah.data

import com.cobasendiri.kasirmudah.data.room.ProductDao
import com.cobasendiri.kasirmudah.data.util.CoroutineMapper.mapExceptionFlow
import com.cobasendiri.kasirmudah.data.util.CoroutineMapper.runMapExceptionSuspending
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.model.ProductDraft
import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository
import com.cobasendiri.kasirmudah.data.util.DataMapper.mapListToDomain
import com.cobasendiri.kasirmudah.data.util.DataMapper.mapToEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ProductRepository(
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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

    override fun getAllProducts(seachQuery: String): Flow<List<ProductInfo>> {
        return productDao.getAllProducts()
            .mapExceptionFlow { products ->
                val filtered = if(seachQuery.isNotEmpty()){
                    products.filter { it.productEntity.name.contains(seachQuery, true) }
                }else {
                    products
                }
                filtered.mapListToDomain()
            }.flowOn(ioDispatcher)
    }

    override suspend fun addNewProduct(
        newProduct: Product
    ) = withContext(ioDispatcher){
        runMapExceptionSuspending {
            productDao.addProduct(newProduct.mapToEntity())
        }
    }

    override suspend fun updateProduct(
        productDraft: ProductDraft
    ) = withContext(ioDispatcher){
        runMapExceptionSuspending {
            productDao.updateProduct(productDraft.mapToEntity())
        }
    }

    override suspend fun deleteProduct(
        productId: String
    ) = withContext(ioDispatcher) {
        runMapExceptionSuspending {
            productDao.deleteProduct(productId)
        }
    }
}

