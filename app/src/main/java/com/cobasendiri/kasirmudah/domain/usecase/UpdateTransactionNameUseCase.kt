package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.repository.ITransactionRepository

class UpdateTransactionNameUseCase(
    private val transactionRepository: ITransactionRepository
) {
    suspend fun invoke(transactionId: String, newName: String): Result<Unit>{
        return try {
            val isNameValid = newName.let {
                it.isNotBlank() && it.firstOrNull()?.isWhitespace() == false
            }
            if(!isNameValid){
                throw KasirMudahException.InvalidInputError
            }

            val result = transactionRepository.updateName(transactionId, newName)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}