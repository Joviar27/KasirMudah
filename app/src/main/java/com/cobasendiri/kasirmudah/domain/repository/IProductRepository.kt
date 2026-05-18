package com.cobasendiri.kasirmudah.domain.repository

import com.cobasendiri.kasirmudah.data.Result
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.model.ProductDraft
import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import kotlinx.coroutines.flow.Flow

interface IProductRepository {

    fun getAllProducts(): Flow<Result<List<ProductInfo>>>

    suspend fun addNewProduct(newProduct: Product): Result<Unit>

    suspend fun updateProduct(productDraft: ProductDraft): Result<Unit>

    suspend fun deleteProduct(productId: String): Result<Unit>

}