package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.model.ProductDraft
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository

class UpdateProductUseCase(
    private val productRepository: IProductRepository
) {
    suspend fun invoke(productDraft: ProductDraft): Result<Unit> {
        return try {
            val result = productRepository.updateProduct(productDraft)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}