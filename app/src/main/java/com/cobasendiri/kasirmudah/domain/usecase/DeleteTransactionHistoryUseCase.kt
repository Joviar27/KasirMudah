package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.repository.ITransactionRepository

class DeleteTransactionHistoryUseCase(
    private val transactionRepository: ITransactionRepository
) {
    suspend fun invoke(transactionId: String): Result<Unit>{
        return try {
            val result = transactionRepository.deleteTransaction(transactionId)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}