package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.model.ProductDraft
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository

class AddProductUseCase(
    private val productRepository: IProductRepository
) {
   suspend fun invoke(newProduct: ProductDraft): Result<Unit>{
        return try {
            val result = productRepository.addNewProduct(newProduct)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
   }
}