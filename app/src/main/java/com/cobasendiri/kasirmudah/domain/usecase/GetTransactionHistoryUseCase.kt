package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.filter.DateFilter
import com.cobasendiri.kasirmudah.domain.model.TransactionHistory
import com.cobasendiri.kasirmudah.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetTransactionHistoryUseCase(
    private val transactionRepository: ITransactionRepository
) {

    fun invoke(timestampFilter: DateFilter): Flow<Result<List<TransactionHistory>>>{
        val timestamp = calculateTimestamp(timestampFilter)
        return transactionRepository.getTransactionHistory(timestamp).map {
            if(it==null){
                Result.Error(KasirMudahException.UnknownError(null))
            }else{
                Result.Success(it)
            }
        }.catch {
            Result.Error(it)
        }
    }

    private fun calculateTimestamp(filter: DateFilter): Long = when (filter) {
        DateFilter.ALL_TIME -> 0L
        DateFilter.TODAY -> System.currentTimeMillis()/1000 - 86400L
        DateFilter.LAST_WEEK -> System.currentTimeMillis()/1000 - (86400L * 7)
        DateFilter.LAST_MONTH -> System.currentTimeMillis()/1000 - (86400L * 30)
    }
}