package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository

class DeleteProductUseCase(
    private val productRepository: IProductRepository
) {
    suspend fun invoke(productId: String): Result<Unit> {
        return try {
            val result = productRepository.deleteProduct(productId)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}