package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.repository.ITransactionRepository

class UpdateTransactionBookmarkUseCase(
    private val transactionRepository: ITransactionRepository
) {
    suspend fun invoke(transactionId: String): Result<Boolean>{
        return try {
            val result = transactionRepository.updateBookmark(transactionId)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}