package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.data.CartRepository
import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.model.TransactionReceiptItem
import com.cobasendiri.kasirmudah.domain.repository.ICartRepository

class GetReceiptItemsUseCase(
    private val cartRepository: ICartRepository
) {
    suspend fun invoke(): Result<List<TransactionReceiptItem>>{
        return try {
            val result = cartRepository.getProductsTotal()
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}