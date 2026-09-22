package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.repository.ICartRepository

class ClearCartUseCase(
    private val cartRepository: ICartRepository
) {
    suspend fun invoke(): Result<Unit> {
        return try {
            val result = cartRepository.clearCart()
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}