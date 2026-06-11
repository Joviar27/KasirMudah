package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.data.TransactionRepository
import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.model.TransactionReceipt

class GetTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun invoke(transactionId: String): Result<TransactionReceipt>{
        return try {
            val result = transactionRepository.getTransaction(transactionId)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}