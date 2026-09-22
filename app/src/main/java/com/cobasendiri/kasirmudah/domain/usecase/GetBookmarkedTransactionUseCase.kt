package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.model.TransactionHistory
import com.cobasendiri.kasirmudah.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetBookmarkedTransactionUseCase(
    private val transactionRepository: ITransactionRepository
) {

    fun invoke(): Flow<Result<List<TransactionHistory>>>{
        return transactionRepository.getBookmarkedTransaction().map {
            if(it==null){
                Result.Error(KasirMudahException.UnknownError(null))
            }else{
                Result.Success(it)
            }
        }.catch {
            Result.Error(it)
        }
    }
}