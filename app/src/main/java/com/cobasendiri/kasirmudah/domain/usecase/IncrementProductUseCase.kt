package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.repository.ICartRepository
import com.cobasendiri.kasirmudah.data.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException

class IncrementProductUseCase(
    private val cartRepository: ICartRepository
) {
    suspend fun invoke(productId: String): Result<Unit>{
        return try {
            val result = cartRepository.addOrIncrementProduct(productId)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}