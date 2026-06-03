package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository

class UpdateProductColorCodeUseCase(
    private val productRepository: IProductRepository
) {
    suspend fun invoke(productId: String, newColor: Long): Result<Unit> {
        return try {
            val result = productRepository.updateProductColorCode(productId, newColor)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}