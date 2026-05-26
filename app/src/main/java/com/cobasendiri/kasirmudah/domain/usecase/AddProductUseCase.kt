package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.data.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository

class AddProductUseCase(
    private val productRepository: IProductRepository
) {
   suspend fun invoke(newProduct: Product): Result<Unit>{
        return try {
            val result = productRepository.addNewProduct(newProduct)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
   }
}